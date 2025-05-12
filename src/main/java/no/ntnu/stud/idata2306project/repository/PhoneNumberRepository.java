package no.ntnu.stud.idata2306project.repository;

import no.ntnu.stud.idata2306project.model.contact.PhoneNumber;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing {@link PhoneNumber} entities.
 *
 * <p>This interface extends the {@link CrudRepository} to 
 * provide CRUD operations for {@link PhoneNumber} entities.
 */
public interface PhoneNumberRepository extends CrudRepository<PhoneNumber, Long> {
  
}
