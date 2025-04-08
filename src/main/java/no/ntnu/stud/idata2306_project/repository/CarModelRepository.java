package no.ntnu.stud.idata2306_project.repository;

import no.ntnu.stud.idata2306_project.model.car.CarModel;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Set;

public interface CarModelRepository extends ListCrudRepository<CarModel, Long> {
  Set<CarModel> findByNameContainingIgnoreCase(String keyword);

  Set<CarModel> findByBrandNameContainingIgnoreCase(String name);
}
