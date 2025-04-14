package no.ntnu.stud.idata2306_project.controller;

import jakarta.annotation.security.PermitAll;
import no.ntnu.stud.idata2306_project.service.CarFilterService;
import no.ntnu.stud.idata2306_project.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.ntnu.stud.idata2306_project.model.car.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cars", description = "Endpoints for managing cars")
@RestController
@RequestMapping("/car")
public class CarController {

  private CarService carService;
  private CarFilterService carFilterService;
  private Logger logger = LoggerFactory.getLogger(CarController.class);

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
    if (filters.isEmpty()) {
      return ResponseEntity.ok(carService.getAllCars());
    } else {
      List<Car> cars = carFilterService.getCarsByFilters(filters);
      logger.info("{} cars found with filters: {}", cars.size(), filters);
      return ResponseEntity.ok(cars);
    }

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

  /**
   * Endpoint for getting cars by name
   *
   * @param keyWord The search term
   * @return List of cars that match the search term
   */
  @Operation(summary = "Get cars by name")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "List of cars that match the search term"),
  })
  @PermitAll
  @GetMapping("/search")
  public ResponseEntity<List<Car>> getByKeyWord(@RequestParam String keyWord) {
    List<Car> cars = this.carService.getCarsByKeyword(keyWord);
    logger.info("{} cars with keyword {} found", cars.size(), keyWord);
    return ResponseEntity.ok(cars);
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
    @ApiResponse(responseCode = "200", description = "Car added"),
  })
  @PostMapping("/")
  public ResponseEntity<String> addCar(@RequestBody Car car) {
    this.carService.saveCar(car);
    logger.info("Car added with id {}", car.getId());

    return ResponseEntity.ok(String.valueOf(car.getId()));
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
