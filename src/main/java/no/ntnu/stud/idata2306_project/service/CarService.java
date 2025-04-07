package no.ntnu.stud.idata2306_project.service;

import no.ntnu.stud.idata2306_project.model.car.Car;
import no.ntnu.stud.idata2306_project.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
  private CarRepository carRepository;

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
