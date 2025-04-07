package no.ntnu.stud.idata2306_project.repository;

import no.ntnu.stud.idata2306_project.model.car.CarBrand;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface CarBrandRepository extends ListCrudRepository<CarBrand, Long> {

  List<CarBrand> findByNameContainingIgnoreCase(String keyword);
}
