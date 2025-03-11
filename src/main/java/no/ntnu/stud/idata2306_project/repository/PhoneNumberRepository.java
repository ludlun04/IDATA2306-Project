package no.ntnu.stud.idata2306_project.repository;

import org.springframework.data.repository.CrudRepository;

import no.ntnu.stud.idata2306_project.model.contact.PhoneNumber;

public interface PhoneNumberRepository extends CrudRepository<PhoneNumber, Long> {
  
}
