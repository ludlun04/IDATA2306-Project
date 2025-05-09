package no.ntnu.stud.idata2306project.repository;

import org.springframework.data.repository.CrudRepository;

import no.ntnu.stud.idata2306project.model.contact.PhoneNumber;

public interface PhoneNumberRepository extends CrudRepository<PhoneNumber, Long> {
  
}
