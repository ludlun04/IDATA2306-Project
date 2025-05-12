package no.ntnu.stud.idata2306project.repository;

import no.ntnu.stud.idata2306project.model.contact.Address;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing {@link Address} entities.
 *
 * <p>This interface extends the {@link CrudRepository} to 
 * provide CRUD operations for {@link Address} entities.
 */
public interface AddressRepository extends CrudRepository<Address, Long> {
  
}
