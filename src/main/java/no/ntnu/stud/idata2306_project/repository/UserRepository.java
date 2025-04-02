package no.ntnu.stud.idata2306_project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.stud.idata2306_project.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
  public Optional<User> findByUsername(String username);
  public List<User> findUsersByUsernameLike(String username);
}
