package no.ntnu.stud.idata2306project.repository;

import java.util.Optional;
import no.ntnu.stud.idata2306project.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link User} entities.
 *
 * <p>This interface extends the {@link JpaRepository} to provide CRUD operations for {@link User}
 * entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {
  public Optional<User> findByEmail(String email);
}
