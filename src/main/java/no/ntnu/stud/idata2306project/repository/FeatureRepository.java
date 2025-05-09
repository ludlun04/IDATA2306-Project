package no.ntnu.stud.idata2306project.repository;

import no.ntnu.stud.idata2306project.model.car.Feature;
import org.springframework.data.repository.ListCrudRepository;

public interface FeatureRepository extends ListCrudRepository<Feature, Long> {
}
