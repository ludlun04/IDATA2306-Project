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
  private final Logger logger = LoggerFactory.getLogger(CarService.class);

  public CarService(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  public List<Car> getAllCars() {
    return carRepository.findAll();
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

}
