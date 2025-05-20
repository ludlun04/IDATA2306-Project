package no.ntnu.stud.idata2306project.repository;

import java.util.Set;
import no.ntnu.stud.idata2306project.model.car.CarBrand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Repository interface for managing {@link CarBrand} entities.
 *
 * <p>This interface extends the {@link ListCrudRepository} to
 * provide CRUD operations for {@link CarBrand} entities.
 * It also includes custom query methods to retrieve car brands based on
 * specific criteria.
 */
public interface CarBrandRepository extends ListCrudRepository<CarBrand, Long> {

  /**
   * Finds a set of car brands whose names contain the specified keyword,
   * ignoring case.
   *
   * @param keyword the keyword to search for in car brand names
   * @return a set of car brands whose names contain the specified keyword
   */
  Set<CarBrand> findByNameContainingIgnoreCase(String keyword);

  /**
   * Finds a set of car brands that are currently in use on cars.
   *
   * @return a set of car brands that are currently in use on cars
   */
  @Query("""
      SELECT DISTINCT brand
            FROM CarBrand brand JOIN Car car ON brand.id = car.model.brand.id
      """)
  Set<CarBrand> findAllInUseOnCars();

}
