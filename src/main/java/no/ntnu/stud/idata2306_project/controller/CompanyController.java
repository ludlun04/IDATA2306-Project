package no.ntnu.stud.idata2306_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.stud.idata2306_project.model.company.Company;
import no.ntnu.stud.idata2306_project.model.user.User;
import no.ntnu.stud.idata2306_project.service.CompanyService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
@Tag(name = "Companies", description = "Endpoints for managing companies")
@RestController
@RequestMapping("/company")
public class CompanyController {

  private final CompanyService companyService;

  /**
   * Creates a new CompanyController.
   *
   * @param companyRepository the repository to use
   */
  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
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
  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping()
  public List<Company> getCompanies() {
    return companyService.getCompanies();
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
  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<Company> getCompany(@PathVariable long id) {
    Company company = companyService.getCompanyById(id);

    if (company != null) {
      return ResponseEntity.status( HttpStatus.OK).body(company);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    
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
  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping()
  public ResponseEntity<Long> addCompany(Company company) {
    companyService.addCompany(company);
    return ResponseEntity.status(HttpStatus.CREATED).body(company.getId());
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
  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Long> deleteCompany(@PathVariable Long id) {
    companyService.deleteCompanyById(id);
    return ResponseEntity.status(HttpStatus.OK).body(id);
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
  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("/{companyId}/user/{userId}")
  public ResponseEntity<String> adduserToCompany(@PathVariable int companyId, @PathVariable int userId) {
    companyService.addUserToCompany(userId, companyId);
    return ResponseEntity.status(HttpStatus.OK).body("User was added to the company");
  }

  /**
   * Removes a user from a company.
   */
  @Operation(summary = "Remove a user from a company", description = "Remove a user from a company")
  @Parameter(name = "companyId", description = "The id of the company to remove the user from", required = true)
  @Parameter(name = "userId", description = "The id of the user to remove from the company", required = true)
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "User was removed from the company"),
    @ApiResponse(responseCode = "404", description = "Company or user not found")
  })
  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{companyId}/user/{userId}")
  public ResponseEntity<String> removeUserFromCompany(@PathVariable int companyId, @PathVariable int userId) {
    companyService.removeUserFromCompany(userId, companyId);
    return ResponseEntity.status(HttpStatus.OK).body("User was removed from the company");
  }


  /**
   * Get all users in a company.
   */
  @Operation(summary = "Get all users in a company", description = "Get all users in a company")
  @Parameter(name = "companyId", description = "The id of the company to get users from", required = true)
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "List of users in the company"),
    @ApiResponse(responseCode = "404", description = "Company not found")
  })
  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/{companyId}/users")
  public ResponseEntity<Set<User>> getUsersInCompany(@PathVariable Long companyId) {
    Set<User> users = this.companyService.getUsersInCompany(companyId);
    if (users != null) {
      return ResponseEntity.status(HttpStatus.OK).body(users);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }
}
