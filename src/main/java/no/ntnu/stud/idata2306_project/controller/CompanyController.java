package no.ntnu.stud.idata2306_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.ntnu.stud.idata2306_project.model.Company;
import no.ntnu.stud.idata2306_project.repository.CompanyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

  private final CompanyRepository companyRepository;

  public CompanyController(CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }

  @Operation(summary = "Get all companies", description = "Get a list of all companies")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "List of companies")
  })
  @GetMapping()
  public List<Company> getCompanies() {
    return companyRepository.findAll();
  }

  @Operation(summary = "Get a company", description = "Get a company by id")
  @Parameter(name = "id", description = "The id of the company to get", required = true)
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Company that was found"),
    @ApiResponse(responseCode = "404", description = "Company not found")
  })
  @GetMapping("/{id}")
  public ResponseEntity<Company> getCompany(int id) {
    Company company = companyRepository.findById(id).orElse(null);
    return ResponseEntity.status(company == null ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(company);
  }

  @Operation(summary = "Add a company", description = "Add a new company")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Company that was added")
  })
  @PostMapping()
  public ResponseEntity<Company> addCompany(Company company) {
    Company newCompany = companyRepository.save(company);
    return ResponseEntity.status(HttpStatus.CREATED).body(newCompany);
  }

  @Operation(summary = "Delete a company", description = "Delete a company by id")
  @Parameter(name = "id", description = "The id of the company to delete", required = true)
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Company that was deleted"),
    @ApiResponse(responseCode = "404", description = "Company not found")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Company> deleteCompany(int id) {
    Company company = companyRepository.findById(id).orElse(null);
    if (company != null) {
      companyRepository.delete(company);
    }
    return ResponseEntity.status(company == null ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(company);
  }
}
