package no.ntnu.stud.idata2306_project.service;

import no.ntnu.stud.idata2306_project.exception.UnknownFilterException;
import no.ntnu.stud.idata2306_project.model.car.Car;
import no.ntnu.stud.idata2306_project.model.car.CarBrand;
import no.ntnu.stud.idata2306_project.model.car.CarModel;
import no.ntnu.stud.idata2306_project.repository.CarBrandRepository;
import no.ntnu.stud.idata2306_project.repository.CarModelRepository;
import no.ntnu.stud.idata2306_project.repository.CarRepository;
import no.ntnu.stud.idata2306_project.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class CarService {
  private final CarRepository carRepository;
  private final CarModelRepository carModelRepository;
  private final CarBrandRepository carBrandRepository;
  private final OrderRepository orderRepository;

  private final Logger logger = LoggerFactory.getLogger(CarService.class);

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public CarService(CarRepository carRepository, CarModelRepository carModelRepository, CarBrandRepository carBrandRepository, OrderRepository orderRepository) {
    this.carRepository = carRepository;
    this.carModelRepository = carModelRepository;
    this.carBrandRepository = carBrandRepository;
    this.orderRepository = orderRepository;
  }

  public List<Car> getAllCars() {
    return carRepository.findAll();
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
        }
        catch (Exception e) {
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
        String [] dates = value.split(",");
        LocalDate startDate = LocalDate.parse(dates[0], formatter);
        LocalDate endDate = LocalDate.parse(dates[1], formatter);
        if (!orderRepository.isAvailableBetween(car.getId(), startDate, endDate)) {
          result = false;
        }
        break;
      case "from price":
        if (car.getPricePerDay() < Double.parseDouble(value)) {
          result = false;
        }
        break;
      case "to price":
        if (car.getPricePerDay() > Double.parseDouble(value)) {
          result = false;
        }
        break;
      default:
        throw new UnknownFilterException(key);
    }
    return result;
  }

  public Optional<Car> getCarById(long id) {
    return carRepository.findById(id);
  }

  public void saveCar(Car car) {
    carRepository.save(car);
  }

  public void deleteCarById(long id) {
    carRepository.deleteById(id);
  }

  public List<Car> getCarsByKeyword(String keyword) {
    Set<CarModel> matchingModels = carModelRepository.findByNameContainingIgnoreCase(keyword);
    Set<CarBrand> matchingBrands = carBrandRepository.findByNameContainingIgnoreCase(keyword);
    Set<Car> matchingCars = new HashSet<>();

    for (CarBrand brand : matchingBrands) {
      Set<CarModel> matchingModelsFromBrand = carModelRepository.findByBrandNameContainingIgnoreCase(brand.getName());
      matchingModels.addAll(matchingModelsFromBrand);
    }

    for (CarModel model : matchingModels) {
      Set<Car> carsByModel = carRepository.findByModel(model);
      matchingCars.addAll(carsByModel);
    }
    return List.copyOf(matchingCars);

  }
}
