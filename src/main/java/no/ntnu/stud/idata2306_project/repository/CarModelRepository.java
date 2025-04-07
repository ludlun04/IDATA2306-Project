package no.ntnu.stud.idata2306_project.repository;

import no.ntnu.stud.idata2306_project.model.car.CarModel;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface CarModelRepository extends ListCrudRepository<CarModel, Long> {
  List<CarModel> findByNameContainingIgnoreCase(String keyword);

  List<CarModel> findByBrandContainingIgnoreCase(String name);
}
