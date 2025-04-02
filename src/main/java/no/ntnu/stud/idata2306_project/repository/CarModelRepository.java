package no.ntnu.stud.idata2306_project.repository;

import no.ntnu.stud.idata2306_project.model.car.CarModel;
import org.springframework.data.repository.ListCrudRepository;

public interface CarModelRepository extends ListCrudRepository<CarModel, Long> {
}
