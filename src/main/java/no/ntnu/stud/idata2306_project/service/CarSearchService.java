package no.ntnu.stud.idata2306_project.service;

import no.ntnu.stud.idata2306_project.model.car.Car;
import no.ntnu.stud.idata2306_project.model.car.CarBrand;
import no.ntnu.stud.idata2306_project.model.car.CarModel;
import no.ntnu.stud.idata2306_project.repository.CarBrandRepository;
import no.ntnu.stud.idata2306_project.repository.CarModelRepository;
import no.ntnu.stud.idata2306_project.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CarSearchService {

  private final CarRepository carRepository;
  private final CarModelRepository carModelRepository;
  private final CarBrandRepository carBrandRepository;

  private final Logger logger = LoggerFactory.getLogger(CarSearchService.class);

  public CarSearchService(CarRepository carRepository, CarModelRepository carModelRepository, CarBrandRepository carBrandRepository) {
    this.carRepository = carRepository;
    this.carModelRepository = carModelRepository;
    this.carBrandRepository = carBrandRepository;
    logger.info("CarSearchService initialized");
  }

  public List<Car> getCarsByKeyword(String keyword) {
    Set<CarModel> matchingModels = carModelRepository.findByNameContainingIgnoreCase(keyword);
    Set<CarBrand> matchingBrands = carBrandRepository.findByNameContainingIgnoreCase(keyword);
    Set<Car> matchingCars = new HashSet<>();

    for (CarBrand brand : matchingBrands) {
      Set<CarModel> matchingModelsFromBrand = carModelRepository.findByBrandNameContainingIgnoreCase(brand.getName());
      matchingModels.addAll(matchingModelsFromBrand);
    }

    for (CarModel model : matchingModels) {
      Set<Car> carsByModel = carRepository.findByModel(model);
      matchingCars.addAll(carsByModel);
    }
    return List.copyOf(matchingCars);

  }
}
