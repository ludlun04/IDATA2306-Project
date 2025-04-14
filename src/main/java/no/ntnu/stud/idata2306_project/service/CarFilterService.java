package no.ntnu.stud.idata2306_project.service;

import no.ntnu.stud.idata2306_project.exception.MissingFilterParameterException;
import no.ntnu.stud.idata2306_project.exception.UnknownFilterException;
import no.ntnu.stud.idata2306_project.model.car.Car;
import no.ntnu.stud.idata2306_project.repository.CarRepository;
import no.ntnu.stud.idata2306_project.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@Service
public class CarFilterService {

  private final OrderRepository orderRepository;
  private final CarRepository carRepository;
  private final CarSearchService carSearchService;

  private final Logger logger = LoggerFactory.getLogger(CarFilterService.class);

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  private static final String FILTER_BRAND = "brand";
  private static final String FILTER_FUEL_TYPE = "fuelType";
  private static final String FILTER_SELLER = "seller";
  private static final String FILTER_SEATS = "seats";
  private static final String FILTER_FROM_TIME = "from_time";
  private static final String FILTER_BETWEEN_TIMES = "between_times";
  private static final String FILTER_FROM_PRICE = "from_price";
  private static final String FILTER_TO_PRICE = "to_price";
  private static final String FILTER_KEYWORD = "keyword";

  public CarFilterService(OrderRepository orderRepository, CarRepository carRepository, CarSearchService carSearchService) {
    this.orderRepository = orderRepository;
    this.carRepository = carRepository;
    this.carSearchService = carSearchService;
  }

  public List<Car> getCarsByFilters(Map<String, String> filters) {
    return carRepository.findAll().stream().filter((Car car) -> {
      boolean fulfillsAllConstraints = true;
      for (String key : filters.keySet()) {
        try {
          if (!fulfillsConstraint(car, key, filters.get(key))) {
            fulfillsAllConstraints = false;
            break;
          }
        } catch (NumberFormatException e) {
          // Handle the case where the value is not a number
          logger.error("Invalid number format for filter: {}, with value: {}", key, filters.get(key));
          fulfillsAllConstraints = false;
          break;
        } catch (UnknownFilterException e) {
          // Handle the case where the filter is unknown
          logger.error("Unknown filter: {}, with value: {}", key, filters.get(key));
          fulfillsAllConstraints = false;
          break;
        } catch (DateTimeParseException e) {
          // Handle the case where the date format is invalid
          logger.error("Invalid date format for filter: {}, with value: {}", key, filters.get(key));
          fulfillsAllConstraints = false;
          break;
        } catch (MissingFilterParameterException e) {
          // Handle the case where a required parameter is missing in filters with multiple values
          logger.error("Missing filter parameter for filter: {}, with value: {}", key, filters.get(key));
          fulfillsAllConstraints = false;
          break;
        } catch (Exception e) {
          // Handle any other exceptions that may occur
          logger.error("An error occurred while checking filter: {}, with value: {}", key, filters.get(key), e);
          fulfillsAllConstraints = false;
          break;
        }

      }
      return fulfillsAllConstraints;
    }).toList();
  }

  private boolean fulfillsConstraint(Car car, String filter, String filterParameter) {
    return switch (filter) {
      case FILTER_BRAND -> hasBrand(car, filterParameter);
      case FILTER_FUEL_TYPE -> hasFuelType(car, filterParameter);
      case FILTER_SELLER -> hasSeller(car, filterParameter);
      case FILTER_SEATS -> hasNumberOfSeats(car, filterParameter);
      case FILTER_FROM_TIME -> isAvailableFromTime(car, filterParameter);
      case FILTER_BETWEEN_TIMES -> isAvailableBetweenTimes(car, filterParameter);
      case FILTER_FROM_PRICE -> costsMoreThan(car, filterParameter);
      case FILTER_TO_PRICE -> costsLessThan(car, filterParameter);
      case FILTER_KEYWORD -> matchesKeyword(car, filterParameter);
      default -> throw new UnknownFilterException(filter);
    };
  }

  private boolean hasBrand(Car car, String value) {
    return car.getModel().getBrand().getName().equalsIgnoreCase(value);
  }

  private boolean hasFuelType(Car car, String value) {
    return car.getFuelType().getName().equalsIgnoreCase(value);
  }

  private boolean hasSeller(Car car, String value) {
    return car.getCompany().getName().equalsIgnoreCase(value);
  }

  private boolean hasNumberOfSeats(Car car, String value) {
    return car.getNumberOfSeats() == Integer.parseInt(value);
  }

  private boolean isAvailableFromTime(Car car, String value) {
    LocalDate fromDate = LocalDate.parse(value, formatter);
    return orderRepository.isAvailableFrom(car.getId(), fromDate);
  }

  private boolean isAvailableBetweenTimes(Car car, String value) {
    try {
      String[] dates = value.split(",");
      LocalDate startDate = LocalDate.parse(dates[0], formatter);
      LocalDate endDate = LocalDate.parse(dates[1], formatter);

      return orderRepository.isAvailableBetween(car.getId(), startDate, endDate);
    } catch (ArrayIndexOutOfBoundsException e) {
      // Missing one of the dates
      throw new MissingFilterParameterException(FILTER_BETWEEN_TIMES, value);
    }
  }

  private boolean costsMoreThan(Car car, String value) {
    return car.getPricePerDay() >= Double.parseDouble(value);
  }

  private boolean costsLessThan(Car car, String value) {
    return car.getPricePerDay() <= Double.parseDouble(value);
  }

  private boolean matchesKeyword(Car car, String keyword) {
    List<Car> matchingCars = carSearchService.getCarsByKeyword(keyword);
    return matchingCars.contains(car);
  }
}

