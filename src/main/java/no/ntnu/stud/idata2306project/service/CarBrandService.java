package no.ntnu.stud.idata2306project.service;

import no.ntnu.stud.idata2306project.model.car.CarBrand;
import no.ntnu.stud.idata2306project.repository.CarBrandRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CarBrandService {
  private CarBrandRepository carBrandRepository;

  public CarBrandService(CarBrandRepository carBrandRepository) {
    this.carBrandRepository = carBrandRepository;
  }

  public Set<CarBrand> getCarBrandsUsedInCars() {
    return carBrandRepository.findAllInUseOnCars();
  }
}
