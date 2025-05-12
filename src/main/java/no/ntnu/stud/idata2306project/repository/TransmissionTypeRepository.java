package no.ntnu.stud.idata2306project.repository;

import no.ntnu.stud.idata2306project.model.car.TransmissionType;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Repository interface for managing {@link TransmissionType} entities.
 *
 * <p>This interface extends the {@link ListCrudRepository} to
 * provide CRUD operations for {@link TransmissionType} entities.
 */
public interface TransmissionTypeRepository extends ListCrudRepository<TransmissionType, Long> {
}
