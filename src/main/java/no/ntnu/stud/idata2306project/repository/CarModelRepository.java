package no.ntnu.stud.idata2306project.repository;

import java.util.Set;
import no.ntnu.stud.idata2306project.model.car.CarModel;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Repository interface for managing {@link CarModel} entities.
 *
 * <p>This interface extends the {@link ListCrudRepository} to
 * provide CRUD operations for {@link CarModel} entities.
 * It also includes custom query methods to retrieve car models based on
 * specific criteria.
 */
public interface CarModelRepository extends ListCrudRepository<CarModel, Long> {
  Set<CarModel> findByNameContainingIgnoreCase(String keyword);

  Set<CarModel> findByBrandNameContainingIgnoreCase(String name);
}
