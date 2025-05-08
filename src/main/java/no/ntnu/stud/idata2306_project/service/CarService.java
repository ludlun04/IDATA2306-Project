package no.ntnu.stud.idata2306_project.service;

import no.ntnu.stud.idata2306_project.model.car.Car;
import no.ntnu.stud.idata2306_project.repository.CarRepository;
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

  public List<Car> getAllVisibleCars() {
    return carRepository.findAllByVisible(true);
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

  public boolean getVisibility(long carId) {
    return carRepository.findById(carId).map(Car::isVisible).orElse(false);
  }

  public void setVisible(long carId, boolean visibility) {
    carRepository.findById(carId).ifPresent(car -> {
        car.setVisible(visibility);
        carRepository.save(car);
        logger.info("Car with id {} is now {}", carId, visibility ? "available" : "not available");
    });
  }
}
