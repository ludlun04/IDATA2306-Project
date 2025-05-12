package no.ntnu.stud.idata2306project.repository;

import java.util.Set;
import no.ntnu.stud.idata2306project.model.car.FuelType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Repository interface for managing {@link FuelType} entities.
 *
 * <p>This interface extends the {@link ListCrudRepository} to
 * provide CRUD operations for {@link FuelType} entities.
 * It also includes custom query methods to retrieve fuel types based on
 * specific criteria.
 */
public interface FuelTypeRepository extends ListCrudRepository<FuelType, Long> {

  @Query("""
      SELECT DISTINCT fuelType
            FROM FuelType fuelType JOIN Car car ON fuelType.id = car.fuelType.id
      """)
  Set<FuelType> getFuelTypesUsedInCars();
}
