package no.ntnu.stud.idata2306_project.service;

import no.ntnu.stud.idata2306_project.exception.InvalidFilterException;
import no.ntnu.stud.idata2306_project.exception.MissingFilterParameterException;
import no.ntnu.stud.idata2306_project.exception.UnknownFilterException;
import no.ntnu.stud.idata2306_project.model.car.Car;
import no.ntnu.stud.idata2306_project.model.car.CarBrand;
import no.ntnu.stud.idata2306_project.model.car.FuelType;
import no.ntnu.stud.idata2306_project.model.company.Company;
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
  private static final String FILTER_FUEL_TYPE = "fuel_type";
  private static final String FILTER_SELLER = "seller";
  private static final String FILTER_SEATS = "seats";
  private static final String FILTER_FROM_TIME = "from_time";
  private static final String FILTER_BETWEEN_TIMES = "between_times";
  private static final String FILTER_FROM_PRICE = "from_price";
  private static final String FILTER_TO_PRICE = "to_price";
  private static final String FILTER_KEYWORD = "keyword";
  private final CarService carService;

  public CarFilterService(OrderRepository orderRepository, CarRepository carRepository, CarSearchService carSearchService, CarService carService) {
    this.orderRepository = orderRepository;
    this.carRepository = carRepository;
    this.carSearchService = carSearchService;
    logger.info("CarFilterService initialized");
    this.carService = carService;
  }

  public List<Car> getCarsByFilters(Map<String, String> filters) throws Exception {
    return carService.getAllVisibleCars().stream().filter((Car car) -> {
      boolean fulfillsAllConstraints = true;
      for (String key : filters.keySet()) {
        String givenParameter = filters.get(key);
        try {
          if (!fulfillsConstraint(car, key, givenParameter)) {
            fulfillsAllConstraints = false;
            break;
          }
        } catch (NumberFormatException e) {
          throw new InvalidFilterException(key, givenParameter, "Invalid number format");
        } catch (UnknownFilterException e) {
          throw new InvalidFilterException(key, givenParameter, "Unknown filter");
        } catch (DateTimeParseException e) {
          throw new InvalidFilterException(key, givenParameter, "Invalid date format");
        } catch (MissingFilterParameterException e) {
          throw new InvalidFilterException(key, givenParameter, "Missing filter parameter");
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
      default -> throw new UnknownFilterException(filter, filterParameter);
    };
  }

  private boolean hasBrand(Car car, String value) {
    boolean result = false;
    String[] brandNames = value.split(",");
    for (String brandName : brandNames) {
      CarBrand brand = car.getModel().getBrand();
      if (brand.getName().equalsIgnoreCase(brandName)) {
        result = true;
        break;
      }
    }
    return result;
  }

  private boolean hasFuelType(Car car, String value) {
    boolean result = false;
    String[] fuelTypes = value.split(",");
    for (String fuelType : fuelTypes) {
      FuelType carFuelType = car.getFuelType();
      if (carFuelType.getName().equalsIgnoreCase(fuelType)) {
        result = true;
        break;
      }
    }
    return result;
  }

  private boolean hasSeller(Car car, String value) {
    boolean result = false;
    String[] sellers = value.split(",");
    for (String seller : sellers) {
      Company company = car.getCompany();
      if (company.getName().equalsIgnoreCase(seller)) {
        result = true;
        break;
      }
    }
    return result;
  }

  private boolean hasNumberOfSeats(Car car, String value) {
    boolean result = false;
    String[] seats = value.split(",");
    for (String seat : seats) {
      int numberOfSeats = car.getNumberOfSeats();
      if (Integer.parseInt(seat) == numberOfSeats) {
        result = true;
        break;
      }
    }
    return result;
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

