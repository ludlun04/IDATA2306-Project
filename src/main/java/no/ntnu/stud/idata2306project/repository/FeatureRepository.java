package no.ntnu.stud.idata2306project.repository;

import no.ntnu.stud.idata2306project.model.car.Feature;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Repository interface for managing {@link Feature} entities.
 *
 * <p>This interface extends the {@link ListCrudRepository} to
 * provide CRUD operations for {@link Feature} entities.
 */
public interface FeatureRepository extends ListCrudRepository<Feature, Long> {
}
