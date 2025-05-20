package no.ntnu.stud.idata2306project.service;

import no.ntnu.stud.idata2306project.model.car.CarBrand;
import no.ntnu.stud.idata2306project.repository.CarBrandRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CarBrandService {
  private CarBrandRepository carBrandRepository;

  /**
   * Creates an instance of CarBrandService.
   *
   * @param carBrandRepository the car brand repository
   */
  public CarBrandService(CarBrandRepository carBrandRepository) {
    this.carBrandRepository = carBrandRepository;
  }

  /**
   * Finds all car brands used in cars.
   *
   * @return a set of car brands used in cars
   */
  public Set<CarBrand> getCarBrandsUsedInCars() {
    return carBrandRepository.findAllInUseOnCars();
  }
}
