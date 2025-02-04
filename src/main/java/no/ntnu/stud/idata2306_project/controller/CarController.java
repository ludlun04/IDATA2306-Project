package no.ntnu.stud.idata2306_project.controller;

import org.springframework.web.bind.annotation.RestController;

import no.ntnu.stud.idata2306_project.model.Car;
import no.ntnu.stud.idata2306_project.repository.CarRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/cars")
public class CarController {
  
  @Autowired
  private CarRepository carRepository;

  @GetMapping("/")
  public List<Car> getAll(@RequestParam String param) {
      return carRepository.findAll();
  }
  

  @GetMapping("/{id}")
  public ResponseEntity<Car> getMethodName(@RequestParam Long id) {
      Optional<Car> car = carRepository.findById(id);

      if (car.isEmpty()) {
          return ResponseEntity.notFound().build();
      }

      return ResponseEntity.ok(car.get());
  }

  @PostMapping("/")
  public ResponseEntity<String> addCar(@RequestBody Car entity) {
      carRepository.save(entity);

      return ResponseEntity.ok(String.valueOf(entity.getId()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCar(@RequestParam Long id) {
      Optional<Car> car = carRepository.findById(id);

      if (car.isEmpty()) {
          return ResponseEntity.notFound().build();
      }

      carRepository.delete(car.get());

      return ResponseEntity.ok("Car deleted");
  }
  

}
