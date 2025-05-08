package no.ntnu.stud.idata2306_project.controller;

import jakarta.validation.Valid;
import no.ntnu.stud.idata2306_project.exception.InvalidFilterException;
import no.ntnu.stud.idata2306_project.service.CarFilterService;
import no.ntnu.stud.idata2306_project.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.ntnu.stud.idata2306_project.model.car.Car;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cars", description = "Endpoints for managing cars")
@RestController
@RequestMapping("/car")
public class CarController {

  private final CarService carService;
  private final CarFilterService carFilterService;
  private final Logger logger = LoggerFactory.getLogger(CarController.class);

  /**
   * Constructor for CarController
   *
   * @param carService The service for cars
   */
  public CarController(CarService carService, CarFilterService carFilterService) {
    this.carService = carService;
    this.carFilterService = carFilterService;
  }

  /**
   * Endpoint to get all cars
   *
   * @return ResponseEntity with a list of all cars
   */
  @Operation(summary = "Get all cars")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "List of cars"),
  })
  @GetMapping()
  public ResponseEntity<List<Car>> getAll(@RequestParam Map<String, String> filters) {

    logger.info("Getting all cars{}", !filters.isEmpty() ? " with filters: " + filters : "");

    ResponseEntity<List<Car>> response;

    if (filters.isEmpty()) {
      response = ResponseEntity.ok(carService.getAllVisibleCars());
    } else {
      try {
        List<Car> cars = carFilterService.getCarsByFilters(filters);
        logger.info("{} cars found with filters: {}", cars.size(), filters);
        response = ResponseEntity.ok(cars);
      } catch (InvalidFilterException e) {
        logger.warn(e.getMessage());
        response = ResponseEntity.badRequest().body(null);
      } catch (Exception e) {
        logger.error("Unknown error encountered while getting cars with filters. {}", e.getMessage());
        response = ResponseEntity.internalServerError().body(null);
      }
    }
    return response;
  }

  /**
   * Endpoint for getting a car by its id
   *
   * @param id The id of the car
   * @return ResponseEntity with the car if it is found
   */
  @Operation(summary = "Get a car by its id")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Car found"),
    @ApiResponse(responseCode = "404", description = "Car not found")
  })
  @GetMapping("/{id}")
  public ResponseEntity<Car> getById(@PathVariable Long id) {
    Optional<Car> car = this.carService.getCarById(id);

    if (car.isEmpty()) {
      logNotFound(id);
      return ResponseEntity.notFound().build();
    }

    logger.info("Car with id {} found", id);
    return ResponseEntity.ok(car.get());
  }

  @GetMapping("/seats")
  public ResponseEntity<List<Integer>> getAllAmountOfSeats() {
    logger.info("Getting all amount of seats");
    return ResponseEntity.ok(carService.getAllAmountOfSeatsInCars().stream().toList());
  }

  /**
   * Endpoint for adding a new car
   *
   * @param car The car to add
   * @return ResponseEntity with the id of the new cars id
   */
  @Parameter(name = "car", description = "The car to add", required = true)
  @Operation(summary = "Add a new car")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Car added"),
  })
  @PostMapping()
  public ResponseEntity<String> addCar(@RequestBody @Valid Car car) {
    logger.info("Adding car with id: {}", car.getId());
    ResponseEntity<String> response;
    try {
      this.carService.saveCar(car);
      logger.info("Car added with id {}", car.getId());

      URI location = URI.create("/car/" + car.getId());
      response = ResponseEntity.created(location).body("Car added with id " + car.getId());
    } catch (InvalidDataAccessApiUsageException e) {
      logger.warn("Invalid car data: {}", e.getMessage());
      response = ResponseEntity.badRequest().body("Given data conflicts with existing data");
    }
    return response;
  }

  /**
   * Endpoint for deleting a car by its id
   *
   * @param id The id of the car to delete
   * @return ResponseEntity with a message if the car was deleted
   */
  @Operation(summary = "Delete a car by its id")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Car deleted"),
    @ApiResponse(responseCode = "404", description = "Car not found")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCar(@PathVariable Long id) {
    Optional<Car> car = carService.getCarById(id);

    if (car.isEmpty()) {
      logNotFound(id);
      return ResponseEntity.notFound().build();
    }

    carService.deleteCarById(car.get().getId());
    logger.info("Car with id {} deleted", id);
    return ResponseEntity.ok("Car deleted");
  }

  private void logNotFound(Long id) {
    logger.warn("Car with id {} not found", id);
  }


}
