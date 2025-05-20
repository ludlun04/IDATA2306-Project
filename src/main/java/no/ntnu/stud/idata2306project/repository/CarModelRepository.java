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

  /**
   * Finds a set of car models whose names contain the specified keyword,
   * ignoring case.
   *
   * @param keyword the keyword to search for in car model names
   * @return a set of car models whose names contain the specified keyword
   */
  Set<CarModel> findByNameContainingIgnoreCase(String keyword);

  /**
   * Finds a set of car models whose brand names contain the specified keyword,
   * ignoring case.
   *
   * @param name the keyword to search for in car brand names
   * @return a set of car models whose brand names contain the specified keyword
   */
  Set<CarModel> findByBrandNameContainingIgnoreCase(String name);
}
