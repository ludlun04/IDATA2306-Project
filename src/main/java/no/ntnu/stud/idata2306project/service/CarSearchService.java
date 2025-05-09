package no.ntnu.stud.idata2306project.service;

import no.ntnu.stud.idata2306project.model.car.Car;
import no.ntnu.stud.idata2306project.model.car.CarBrand;
import no.ntnu.stud.idata2306project.model.car.CarModel;
import no.ntnu.stud.idata2306project.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarSearchService {

  private final CarRepository carRepository;

  private final Logger logger = LoggerFactory.getLogger(CarSearchService.class);

  public CarSearchService(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  public List<Car> getCarsByKeyword(String keyword) {
    String keywordCopy = keyword.toLowerCase();

    List<Car> cars = this.carRepository.findAll();
    Map<Car, String> carWithFullNames = new HashMap<>();
    for (Car car : cars) {
      CarModel model = car.getModel();
      CarBrand brand = model.getBrand();
      String fullName = brand.getName() + " " + model.getName();
      carWithFullNames.put(car, fullName);
    }

    return carWithFullNames.entrySet().stream()
        .filter(entry -> entry.getValue().toLowerCase().contains(keywordCopy))
        .map(Map.Entry::getKey)
        .toList();
  }
}
