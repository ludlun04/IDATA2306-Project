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

  private boolean fulfillsConstraint(Car car, String key, String value) {
    boolean result = true;
    switch(key) {
      case "brand":
        if (!car.getModel().getBrand().getName().equalsIgnoreCase(value)) {
          result = false;
        }
        break;
      case "fuelType":
        if (!car.getFuelType().getName().equalsIgnoreCase(value)) {
          result = false;
        }
        break;
      case "seller":
        if (!car.getCompany().getName().equalsIgnoreCase(value)) {
          result = false;
        }
        break;
      case "seats":
        if (car.getNumberOfSeats() != Integer.parseInt(value)) {
          result = false;
        }
        break;
      case "from_time":
        LocalDate fromDate = LocalDate.parse(value, formatter);
        if (!orderRepository.isAvailableFrom(car.getId(), fromDate)) {
          result = false;
        }
        break;
      case "between_times":
        try {
          String [] dates = value.split(",");
          LocalDate startDate = LocalDate.parse(dates[0], formatter);
          LocalDate endDate = LocalDate.parse(dates[1], formatter);
          if (!orderRepository.isAvailableBetween(car.getId(), startDate, endDate)) {
            result = false;
          }
        } catch (ArrayIndexOutOfBoundsException e) {
          // Missing one of the dates
          throw new MissingFilterParameterException(key, value);
        }
        break;
      case "from_price":
        if (car.getPricePerDay() < Double.parseDouble(value)) {
          result = false;
        }
        break;
      case "to_price":
        if (car.getPricePerDay() > Double.parseDouble(value)) {
          result = false;
        }
        break;
      case "keyword":
        List<Car> matchingCars = carSearchService.getCarsByKeyword(value);
        if (!matchingCars.contains(car)) {
          result = false;
        }
        break;
      default:
        throw new UnknownFilterException(key);
    }
    return result;
  }
}
