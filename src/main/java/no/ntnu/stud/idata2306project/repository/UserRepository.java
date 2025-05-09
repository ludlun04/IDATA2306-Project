package no.ntnu.stud.idata2306project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.stud.idata2306project.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
  public Optional<User> findByEmail(String email);
}
