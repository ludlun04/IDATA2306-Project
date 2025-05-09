package no.ntnu.stud.idata2306project.repository;

import no.ntnu.stud.idata2306project.model.car.FuelType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Set;

public interface FuelTypeRepository extends ListCrudRepository<FuelType, Long> {

  @Query(
    """
      SELECT DISTINCT fuelType
            FROM FuelType fuelType JOIN Car car ON fuelType.id = car.fuelType.id
      """
  )
  Set<FuelType> getFuelTypesUsedInCars();
}
