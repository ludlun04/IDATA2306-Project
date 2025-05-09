package no.ntnu.stud.idata2306project.service;

import no.ntnu.stud.idata2306project.model.car.FuelType;
import no.ntnu.stud.idata2306project.repository.FuelTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FuelTypeService {

  private final FuelTypeRepository fuelTypeRepository;

  public FuelTypeService(FuelTypeRepository fuelTypeRepository) {
    this.fuelTypeRepository = fuelTypeRepository;
  }

  public Set<FuelType> getFuelTypesUsedInCars() {
    return fuelTypeRepository.getFuelTypesUsedInCars();
  }
}
