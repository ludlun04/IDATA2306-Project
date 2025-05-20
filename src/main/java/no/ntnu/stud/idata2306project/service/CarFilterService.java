package no.ntnu.stud.idata2306project.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import no.ntnu.stud.idata2306project.exception.InvalidFilterException;
import no.ntnu.stud.idata2306project.exception.MissingFilterParameterException;
import no.ntnu.stud.idata2306project.exception.UnknownFilterException;
import no.ntnu.stud.idata2306project.model.car.Car;
import no.ntnu.stud.idata2306project.model.car.CarBrand;
import no.ntnu.stud.idata2306project.model.car.FuelType;
import no.ntnu.stud.idata2306project.model.company.Company;
import no.ntnu.stud.idata2306project.repository.OrderRepository;

import org.springframework.stereotype.Service;

/**
 * Service class for filtering cars based on various criteria.
 */
@Service
public class CarFilterService {

  private final OrderRepository orderRepository;
  private final CarSearchService carSearchService;
  private final CompanyService companyService;

  

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

  /**
   * Constructor for CarFilterService.
   *
   * @param orderRepository the order repository
   * @param carSearchService the car search service
   * @param carService the car service
   */
  public CarFilterService(OrderRepository orderRepository, CarSearchService carSearchService, 
      CarService carService, CompanyService companyService) {
    this.orderRepository = orderRepository;
    this.carSearchService = carSearchService;
    this.carService = carService;
    this.companyService = companyService;
  }

  /**
   * Filters the cars based on the provided filters.
   *
   * @param filters a map of filters to apply
   * @return a list of cars that match the filters
   */
  public List<Car> getCarsByFilters(Map<String, String> filters) {
    return carService.getAllVisibleCars().stream().filter(
      (Car car) -> fulfillsAllConstraints(filters, car)).toList();
  }

  /**
   * Checks if a car fulfills all the constraints specified in the filters.
   *
   * @param filters a map of filters to apply
   * @param car the car to check
   * @return true if the car fulfills all constraints, false otherwise
   */
  private boolean fulfillsAllConstraints(Map<String, String> filters, Car car) {
    boolean fulfillsAllConstraints = true;
    for (Map.Entry<String, String> entry : filters.entrySet()) {
      String filter = entry.getKey();
      String givenParameter = entry.getValue();
      try {
        if (!fulfillsConstraint(car, filter, givenParameter)) {
          fulfillsAllConstraints = false;
          break;
        }
      } catch (NumberFormatException e) {
        throw new InvalidFilterException(filter, givenParameter, "Invalid number format");
      } catch (UnknownFilterException e) {
        throw new InvalidFilterException(filter, givenParameter, "Unknown filter");
      } catch (DateTimeParseException e) {
        throw new InvalidFilterException(filter, givenParameter, "Invalid date format");
      } catch (MissingFilterParameterException e) {
        throw new InvalidFilterException(filter, givenParameter, "Missing filter parameter");
      }

    }
    return fulfillsAllConstraints;
  }

  /**
   * Checks if a car fulfills a specific constraint based on the filter and filter parameter.
   *
   * @param car the car to check
   * @param filter the filter to apply
   * @param filterParameter the filter parameter
   * @return true if the car fulfills the constraint, false otherwise
   */
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

  /**
   * Checks if a car has a specific brand.
   *
   * @param car the car to check
   * @param value the brand value to check against
   * @return true if the car has the brand, false otherwise
   */
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

  /**
   * Checks if a car has a specific fuel type.
   *
   * @param car the car to check
   * @param value the fuel type value to check against
   * @return true if the car has the fuel type, false otherwise
   */
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

  /**
   * Checks if a car has a specific seller.
   *
   * @param car the car to check
   * @param value the seller value to check against
   * @return true if the car has the seller, false otherwise
   */
  private boolean hasSeller(Car car, String value) {
    boolean result = false;

    List<String> sellers = Arrays.stream(value.split(",")).toList();
    sellers = sellers.stream().map(String::toLowerCase).toList();

    List<Company> companies = companyService.getCompanies();
    for (Company company : companies) {
      if (sellers.contains(company.getName().toLowerCase())
          && company.getCars().contains(car)
      ) {
        result = true;
        break;
      }
    }
    return result;
  }

  /**
   * Checks if a car has a specific number of seats.
   *
   * @param car the car to check
   * @param value the number of seats value to check against
   * @return true if the car has the number of seats, false otherwise
   */
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

  /**
   * Checks if a car is available from a specific date.
   *
   * @param car the car to check
   * @param value the date value to check against
   * @return true if the car is available from the date, false otherwise
   */
  private boolean isAvailableFromTime(Car car, String value) {
    LocalDate fromDate = LocalDate.parse(value, formatter);
    return orderRepository.isAvailableFrom(car.getId(), fromDate);
  }

  /**
   * Checks if a car is available between two dates.
   *
   * @param car the car to check
   * @param value the date range value to check against
   * @return true if the car is available between the dates, false otherwise
   */
  private boolean isAvailableBetweenTimes(Car car, String value) {
    try {
      String[] dates = value.split(",");
      LocalDate startDate = LocalDate.parse(dates[0], formatter);
      LocalDate endDate = LocalDate.parse(dates[1], formatter);

      boolean isAvailable = orderRepository.isAvailableBetween(car.getId(), startDate, endDate);
      return isAvailable;
    } catch (ArrayIndexOutOfBoundsException e) {
      // Missing one of the dates
      throw new MissingFilterParameterException(FILTER_BETWEEN_TIMES, value);
    }
  }

  /**
   * Checks if a car costs more than a specific value.
   *
   * @param car the car to check
   * @param value the price value to check against
   * @return true if the car costs more than the value, false otherwise
   */
  private boolean costsMoreThan(Car car, String value) {
    return car.getPricePerDay() >= Double.parseDouble(value);
  }

  /**
   * Checks if a car costs less than a specific value.
   *
   * @param car the car to check
   * @param value the price value to check against
   * @return true if the car costs less than the value, false otherwise
   */
  private boolean costsLessThan(Car car, String value) {
    return car.getPricePerDay() <= Double.parseDouble(value);
  }

    /**
     * Checks if a car matches a specific keyword.
     *
     * @param car the car to check
     * @param keyword the keyword to check against
     * @return true if the car matches the keyword, false otherwise
     */
  private boolean matchesKeyword(Car car, String keyword) {
    List<Car> matchingCars = carSearchService.getCarsByKeyword(keyword);
    return matchingCars.contains(car);
  }
}

