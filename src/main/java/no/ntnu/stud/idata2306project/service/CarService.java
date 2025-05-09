package no.ntnu.stud.idata2306project.service;

import no.ntnu.stud.idata2306project.model.car.Car;
import no.ntnu.stud.idata2306project.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

  public Set<Integer> getAllAmountOfSeatsInCars() {
    Set<Integer> amountOfSeats = carRepository.findAllAmountOfSeatsInCars();
    for (Integer amount : amountOfSeats) {
      logger.info("Amount of seats: {}", amount);
    }
    return amountOfSeats;
  }
}
