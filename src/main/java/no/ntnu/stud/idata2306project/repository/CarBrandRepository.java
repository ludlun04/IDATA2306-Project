package no.ntnu.stud.idata2306project.repository;

import no.ntnu.stud.idata2306project.model.car.CarBrand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Set;

public interface CarBrandRepository extends ListCrudRepository<CarBrand, Long> {

  Set<CarBrand> findByNameContainingIgnoreCase(String keyword);

  @Query(
    """
      SELECT DISTINCT brand
            FROM CarBrand brand JOIN Car car ON brand.id = car.model.brand.id
      """
  )
  Set<CarBrand> findAllInUseOnCars();

}
