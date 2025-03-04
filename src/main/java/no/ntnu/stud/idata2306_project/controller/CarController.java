package no.ntnu.stud.idata2306_project.controller;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.ntnu.stud.idata2306_project.model.Car;
import no.ntnu.stud.idata2306_project.repository.CarRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
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

  public CarController(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  
  @Operation(summary = "Get all cars")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "List of cars"),
  })
  @GetMapping()
  public ResponseEntity<List<Car>> getAll() {
      return ResponseEntity.ok(carRepository.findAll());
  }
  

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

  @Operation(summary = "Get cars by name")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "List of cars that match the search term"),
  })
  @GetMapping("/search/{keyWord}")
  public List<Car> getByKeyWord(@PathVariable String keyWord) {
      return carRepository.findByNameContaining(keyWord);
  }

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
