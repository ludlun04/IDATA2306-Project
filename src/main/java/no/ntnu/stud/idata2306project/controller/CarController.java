package no.ntnu.stud.idata2306project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import no.ntnu.stud.idata2306project.exception.CarNotFoundException;
import no.ntnu.stud.idata2306project.exception.CompanyNotFoundException;
import no.ntnu.stud.idata2306project.exception.InvalidFilterException;
import no.ntnu.stud.idata2306project.exception.UnauthorizedException;
import no.ntnu.stud.idata2306project.model.car.Car;
import no.ntnu.stud.idata2306project.model.company.Company;
import no.ntnu.stud.idata2306project.security.AccessUserDetails;
import no.ntnu.stud.idata2306project.service.CarFilterService;
import no.ntnu.stud.idata2306project.service.CarService;
import no.ntnu.stud.idata2306project.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represents a controller for cars.
 *
 * <p>Contains the following endpoints:
 * <ul>
 *   <li> Get all cars
 *   <li> Get a car by its id
 *   <li> Get all amount of seats
 *   <li> Add a new car
 *   <li> Update a car's visibility
 *   <li> Delete a car by its id
 * </ul>
 */
@Tag(name = "Cars", description = "Endpoints for managing cars")
@RestController
@RequestMapping("/car")
public class CarController {

  private final CarService carService;
  private final CarFilterService carFilterService;
  private final Logger logger = LoggerFactory.getLogger(CarController.class);

  /**
   * Create a new CarController.
   *
   * @param carService       The service for cars
   * @param carFilterService The service for filtering cars
   * @param companyService   The service for companies
   */
  public CarController(
      CarService carService,
      CarFilterService carFilterService) {
    this.carService = carService;
    this.carFilterService = carFilterService;
  }

  /**
   * Endpoint to get all cars.
   *
   * <p>Note that cars marked as not visible will not be included.</p>
   *
   * @return ResponseEntity with a list of all cars
   */
  @Operation(summary = "Get all cars",
      description = "Get all cars. Cars marked as not visible will not be included.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of cars"),
      @ApiResponse(responseCode = "400", description = "Invalid filter"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping()
  public ResponseEntity<List<Car>> getAll(
      @Parameter(name = "filters",
          description = "Filters to apply when filtering which cars get returned")
      @RequestParam Map<String, String> filters
  ) {

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
        logger.error("Unknown error encountered while getting cars with filters. {}",
            e.getMessage());
        response = ResponseEntity.internalServerError().body(null);
      }
    }
    return response;
  }

  /**
   * Endpoint for getting a car by its id.
   *
   * @param id The id of the car
   * @return ResponseEntity with the car if it is found
   */
  @Operation(summary = "Get a car by its id",
      description = "Get a car by its id.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Car found"),
      @ApiResponse(responseCode = "404", description = "Car not found")
  })
  @GetMapping("/{id}")
  public ResponseEntity<Car> getById(
      @Parameter(name = "id", description = "The id of the car", required = true)
      @PathVariable Long id
  ) {
    Optional<Car> car = this.carService.getCarById(id);

    if (car.isEmpty()) {
      logNotFound(id);
      return ResponseEntity.notFound().build();
    }

    logger.info("Car with id {} found", id);
    return ResponseEntity.ok(car.get());
  }

  /**
   * Endpoint for getting all amount of seats in cars.
   *
   * @return ResponseEntity with a list of all amount of seats in cars
   */
  @Operation(summary = "Get all amount of seats in cars",
      description = "Get all amount of seats in cars.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of amount of seats in cars")
  })
  @GetMapping("/seats")
  public ResponseEntity<List<Integer>> getAllAmountOfSeats() {
    logger.info("Getting all amount of seats");
    return ResponseEntity.ok(carService.getAllAmountOfSeatsInCars().stream().toList());
  }

  /**
   * Endpoint for adding a new car.
   *
   * @param car The car to add
   * @return ResponseEntity with the id of the new cars id
   */
  @Operation(summary = "Add a new car",
      description = "Add a new car.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Car added"),
      @ApiResponse(responseCode = "400", description = "Invalid car data"),
  })
  @PostMapping()
  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  public ResponseEntity<String> addCar(
      @Parameter(name = "car", description = "The car to add", required = true)
      @RequestBody @Valid Car car
  ) {
    logger.info("Adding car with id: {}", car.getId());
    ResponseEntity<String> response;
    try {
      this.carService.saveCar(car);
      logger.info("{}", car.getId());

      URI location = URI.create("/car/" + car.getId());
      response = ResponseEntity.created(location).body(""+car.getId());
    } catch (InvalidDataAccessApiUsageException e) {
      logger.warn("Invalid car data: {}", e.getMessage());
      response = ResponseEntity.badRequest().body("Given data conflicts with existing data");
    }
    return response;
  }

  /**
   * Endpoint for updating a car's visibility. User must be logged in and be a part of the company
   * that owns the car.
   *
   * @param id      The id of the car
   * @param visible The visibility of the car
   * @return ResponseEntity with the car if it is found
   */
  @Operation(summary = "Update a car's visibility")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Car found, visibility updated"),
      @ApiResponse(responseCode = "403", description = "User not authorized to update car"),
      @ApiResponse(responseCode = "404", description = "Car not found")
  })
  @PreAuthorize("hasAuthority('USER')")
  @PutMapping("/{id}/visibility")
  public ResponseEntity<Car> updateCarVisibility(
      @Parameter(name = "id", description = "The id of the car", required = true)
      @PathVariable Long id,
      @Parameter(name = "visible", description = "The visibility of the car", required = true)
      @RequestBody boolean visible
  ) {
    Optional<Car> car = this.carService.getCarById(id);
    if (car.isEmpty()) {
      logNotFound(id);
      return ResponseEntity.notFound().build();
    }

    AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder
        .getContext().getAuthentication().getPrincipal();

    try {
      carService.setVisible(userDetails.getId(), id, visible);
      logger.info("Car with id {} updated", id);
    } catch (UnauthorizedException e) {
      logger.warn("User with id {} is not authorized to update car with id {}", userDetails.getId(), id);
      return ResponseEntity.status(403).build();
    } catch (CarNotFoundException e) {
      logNotFound(id);
      return ResponseEntity.notFound().build();
    } catch (CompanyNotFoundException e) {
      logger.warn("No company found that owns car with id {}", id);
    }

    return ResponseEntity.ok(car.get());
  }

  /**
   * Endpoint for deleting a car by its id.
   *
   * @param id The id of the car to delete
   * @return ResponseEntity with a message if the car was deleted
   */
  @Operation(summary = "Delete a car by its id",
      description = "Delete a car by its id.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Car deleted"),
      @ApiResponse(responseCode = "404", description = "Car not found")
  })
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<String> deleteCar(
      @Parameter(name = "id", description = "The id of the car", required = true)
      @PathVariable Long id
  ) {
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
