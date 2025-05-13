package no.ntnu.stud.idata2306project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import no.ntnu.stud.idata2306project.model.car.Car;
import no.ntnu.stud.idata2306project.model.car.CarBrand;
import no.ntnu.stud.idata2306project.model.car.CarModel;
import no.ntnu.stud.idata2306project.repository.CarRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for searching cars based on a keyword.
 */
@Service
public class CarSearchService {

  private final CarRepository carRepository;

  public CarSearchService(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  /**
   * Searches for cars based on a keyword. The keyword is matched against the car's brand and model
   * names.
   *
   * @param keyword the keyword to search for
   * @return a list of cars that match the keyword
   */
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
