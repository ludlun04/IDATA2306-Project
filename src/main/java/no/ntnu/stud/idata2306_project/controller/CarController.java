package no.ntnu.stud.idata2306_project.controller;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.ntnu.stud.idata2306_project.model.car.Car;
import no.ntnu.stud.idata2306_project.repository.CarRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/cars")
public class CarController {
  
  private CarRepository carRepository;

  /**
   * Constructor for CarController
   * 
   * @param carRepository The repository for cars
   */
  public CarController(CarRepository carRepository) {
    this.carRepository = carRepository;
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
  @PreAuthorize("hasRole('User')")
  @GetMapping()
  public ResponseEntity<List<Car>> getAll() {
      return ResponseEntity.ok(carRepository.findAll());
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
  public ResponseEntity<Car> getMethodName(@PathVariable Long id) {
      Optional<Car> car = carRepository.findById(id);

      if (car.isEmpty()) {
          return ResponseEntity.notFound().build();
      }

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
  @GetMapping("/search/{keyWord}")
  public List<Car> getByKeyWord(@PathVariable String keyWord) {
      return null;//carRepository.findByNameContaining(keyWord);
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
      carRepository.save(car);

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
      Optional<Car> car = carRepository.findById(id);

      if (car.isEmpty()) {
          return ResponseEntity.notFound().build();
      }

      carRepository.delete(car.get());

      return ResponseEntity.ok("Car deleted");
  }
  

}
