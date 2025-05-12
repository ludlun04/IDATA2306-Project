package no.ntnu.stud.idata2306project.repository;

import java.util.Optional;
import no.ntnu.stud.idata2306project.model.user.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing {@link Role} entities.
 *
 * <p>This interface extends the {@link CrudRepository} to provide CRUD operations for {@link Role}
 * entities.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
  Optional<Role> findByName(String name);
}
