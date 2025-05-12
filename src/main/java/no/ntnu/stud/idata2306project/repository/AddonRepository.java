package no.ntnu.stud.idata2306project.repository;

import no.ntnu.stud.idata2306project.model.car.Addon;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Repository interface for managing {@link Addon} entities.
 *
 * <p>This interface extends the {@link ListCrudRepository} to 
 * provide CRUD operations for {@link Addon} entities.
 */
public interface AddonRepository extends ListCrudRepository<Addon, Long> {

}
