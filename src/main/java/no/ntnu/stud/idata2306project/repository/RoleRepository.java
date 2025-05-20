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

  /**
   * Finds a role by its name.
   *
   * @param name the name of the role to find
   * @return an {@link Optional} containing the found role, or empty if no role with the specified
   *     name exists
   */
  Optional<Role> findByName(String name);
}
