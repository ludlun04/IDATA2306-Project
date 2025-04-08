package no.ntnu.stud.idata2306_project.service;

import no.ntnu.stud.idata2306_project.model.car.Car;
import no.ntnu.stud.idata2306_project.model.car.CarBrand;
import no.ntnu.stud.idata2306_project.model.car.CarModel;
import no.ntnu.stud.idata2306_project.repository.CarBrandRepository;
import no.ntnu.stud.idata2306_project.repository.CarModelRepository;
import no.ntnu.stud.idata2306_project.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
  private CarRepository carRepository;
  private CarModelRepository carModelRepository;
  private CarBrandRepository carBrandRepository;

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

  public List<Car> getCarsByKeyword(String keyword) {
    List<CarModel> matchingModels = carModelRepository.findByNameContainingIgnoreCase(keyword);
    List<CarBrand> matchingBrands = carBrandRepository.findByNameContainingIgnoreCase(keyword);
    List<Car> matchingCars = new ArrayList<>();

    for (CarBrand brand : matchingBrands) {
      List<CarModel> matchingModelsFromBrand = carModelRepository.findByBrandNameContainingIgnoreCase(brand.getName());
      matchingModels.addAll(matchingModelsFromBrand);
    }

    for (CarModel model : matchingModels) {
      List<Car> carsByModel = carRepository.findByModel(model);
      matchingCars.addAll(carsByModel);
    }
    return matchingCars;

  }
}
