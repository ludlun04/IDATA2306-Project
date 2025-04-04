package no.ntnu.stud.idata2306_project.repository;

import org.springframework.data.repository.ListCrudRepository;

import no.ntnu.stud.idata2306_project.model.company.Company;

public interface CompanyRepository extends ListCrudRepository<Company, Long> {
}
