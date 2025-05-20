package no.ntnu.stud.idata2306project.service;

import no.ntnu.stud.idata2306project.model.car.FuelType;
import no.ntnu.stud.idata2306project.repository.FuelTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FuelTypeService {

  private final FuelTypeRepository fuelTypeRepository;

  /**
   * Creates an instance of FuelTypeService.
   *
   * @param fuelTypeRepository the fuel type repository
   */
  public FuelTypeService(FuelTypeRepository fuelTypeRepository) {
    this.fuelTypeRepository = fuelTypeRepository;
  }

  /**
   * Get all fuel types that are used by cars.
   *
   * @return a set of all fuel types used by cars
   */
  public Set<FuelType> getFuelTypesUsedInCars() {
    return fuelTypeRepository.getFuelTypesUsedInCars();
  }
}
