package no.ntnu.stud.idata2306_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.ntnu.stud.idata2306_project.model.company.Company;
import no.ntnu.stud.idata2306_project.repository.CompanyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Represents a controller for companies.
 *
 * <p>Contains the following endpoints:
 * <ul>
 *  <li>GET /company: Get all companies</li>
 *  <li>GET /company/{id}: Get a company by id</li>
 *  <li>POST /company: Add a new company</li>
 *  <li>DELETE /company/{id}: Delete a company by id</li>
 *  </ul>
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

  private final CompanyRepository companyRepository;

  /**
   * Creates a new CompanyController.
   *
   * @param companyRepository the repository to use
   */
  public CompanyController(CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }

  /**
   * Get all companies.
   *
   * @return a list of all companies
   */
  @Operation(summary = "Get all companies", description = "Get a list of all companies")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "List of companies")
  })
  @GetMapping()
  public List<Company> getCompanies() {
    return companyRepository.findAll();
  }

  /**
   * Get a company by its id.
   *
   * @param id the id of the company to get
   * @return the company that was found
   */
  @Operation(summary = "Get a company", description = "Get a company by id")
  @Parameter(name = "id", description = "The id of the company to get", required = true)
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Company that was found"),
    @ApiResponse(responseCode = "404", description = "Company not found")
  })
  @GetMapping("/{id}")
  public ResponseEntity<Company> getCompany(@PathVariable int id) {
    Company company = companyRepository.findById(id).orElse(null);
    return ResponseEntity.status(company == null ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(company);
  }

  /**
   * Add a new company.
   *
   * @param company the company to add
   * @return the company that was added
   */
  @Operation(summary = "Add a company", description = "Add a new company")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Company that was added")
  })
  @PostMapping()
  public ResponseEntity<Company> addCompany(Company company) {
    Company newCompany = companyRepository.save(company);
    return ResponseEntity.status(HttpStatus.CREATED).body(newCompany);
  }

  /**
   * Delete a company by its id.
   *
   * @param id the id of the company to delete
   * @return the company that was deleted
   */
  @Operation(summary = "Delete a company", description = "Delete a company by id")
  @Parameter(name = "id", description = "The id of the company to delete", required = true)
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Company that was deleted"),
    @ApiResponse(responseCode = "404", description = "Company not found")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Company> deleteCompany(@PathVariable int id) {
    Company company = companyRepository.findById(id).orElse(null);
    if (company != null) {
      companyRepository.delete(company);
    }
    return ResponseEntity.status(company == null ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(company);
  }


  /**
   * Adds a user to a company.
   */
  @Operation(summary = "Add a user to a company", description = "Add a user to a company")
  @Parameter(name = "companyId", description = "The id of the company to add the user to", required = true)
  @Parameter(name = "userId", description = "The id of the user to add to the company", required = true)
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "User was added to the company"),
    @ApiResponse(responseCode = "404", description = "Company or user not found")
  })
  @PostMapping("/{companyId}/user/{userId}")
  public ResponseEntity<String> adduserToCompany(@PathVariable int companyId, @PathVariable int userId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Not implemented yet");
  }
}
