package no.ntnu.stud.idata2306_project.repository;

import no.ntnu.stud.idata2306_project.model.car.CarBrand;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Set;

public interface CarBrandRepository extends ListCrudRepository<CarBrand, Long> {

  Set<CarBrand> findByNameContainingIgnoreCase(String keyword);
}
