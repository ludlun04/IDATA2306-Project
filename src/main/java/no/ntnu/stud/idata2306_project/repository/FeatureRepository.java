package no.ntnu.stud.idata2306_project.repository;

import no.ntnu.stud.idata2306_project.model.car.Feature;
import org.springframework.data.repository.ListCrudRepository;

public interface FeatureRepository extends ListCrudRepository<Feature, Long> {
}
