package no.ntnu.stud.idata2306project.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import no.ntnu.stud.idata2306project.model.user.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
  Optional<Role> findByName(String name);
}
