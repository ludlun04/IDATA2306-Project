package no.ntnu.stud.idata2306_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.stud.idata2306_project.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  
}
