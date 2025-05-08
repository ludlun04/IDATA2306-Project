package no.ntnu.stud.idata2306_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.stud.idata2306_project.model.car.Car;
import no.ntnu.stud.idata2306_project.model.company.Company;
import no.ntnu.stud.idata2306_project.model.user.User;
import no.ntnu.stud.idata2306_project.security.AccessUserDetails;
import no.ntnu.stud.idata2306_project.service.CompanyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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

  private Logger logger = LoggerFactory.getLogger(CompanyController.class);

  /**
   * Creates a new CompanyController.
   *
   * @param companyService the service to use
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
    this.logger.info("Getting companies");
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
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<Company> getCompany(@PathVariable long id, @AuthenticationPrincipal AccessUserDetails userDetails) {
    boolean isAdmin = userDetails.getAuthorities().stream()
            .anyMatch(predicate -> predicate.getAuthority().equals("ADMIN"));

    Company company = companyService.getCompanyById(id);

    boolean isUserInCompany = false;

    if (company != null) {
      isUserInCompany = company.getUsers().stream()
              .anyMatch(user -> user.getId().equals(userDetails.getId()));
    }

    if (isAdmin && company != null) {
      this.logger.info("Company found with id {} by admin user", id);
      return ResponseEntity.status(HttpStatus.OK).body(company);
    } else if (isUserInCompany) {
      return ResponseEntity.status(HttpStatus.OK).body(company);
    } else {
      this.logger.error("Company not found with id {} by user {}", id, userDetails.getId());
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
    this.logger.info("Adding company {}", company.getName());
    companyService.addCompany(company);
    this.logger.info("Company added with id {}", company.getId());
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
    this.logger.info("Deleting company with id {}", id);
    companyService.deleteCompanyById(id);
    this.logger.info("Company deleted with id {}", id);
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
    this.logger.info("Adding user {} to company {}", userId, companyId);
    companyService.addUserToCompany(userId, companyId);
    this.logger.info("User {} added to company {}", userId, companyId);
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
    this.logger.info("Removing user {} from company {}", userId, companyId);
    companyService.removeUserFromCompany(userId, companyId);
    this.logger.info("User {} removed from company {}", userId, companyId);
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
    this.logger.info("Getting users in company {}", companyId);
    Set<User> users = this.companyService.getUsersInCompany(companyId);
    if (users != null) {
      return ResponseEntity.status(HttpStatus.OK).body(users);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  /**
   * Get all companies used in cars.
   *
   * @return a set of all companies used in cars
   */
  @Operation(summary = "Get all companies used in cars", description = "Get a set of all companies used in cars")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Set of companies used in cars")
  })
  @GetMapping("/with_rentals")
  public ResponseEntity<Set<Company>> getAllCompaniesUsedInCars() {
    Set<Company> companies = this.companyService.getAllCompaniesUsedInCars();

    logger.info("Getting all companies used in cars");

    return ResponseEntity.status(HttpStatus.OK).body(companies);
  }

  /**
   * Get all companies.
   *
   * @return a set of all companies
   */
  @Operation(summary = "Get all companies", description = "Get a set of all companies")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Set of all companies"),
      @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PreAuthorize("hasAnyAuthority('ADMIN')")
  @GetMapping("/all")
  public ResponseEntity<List<Company>> getAllCompanies() {
      List<Company> companies = this.companyService.getCompanies();

      logger.info("Getting all companies");

      return ResponseEntity.status(HttpStatus.OK).body(companies);
  }

  /**
   * Get all companies associated with the current user.
   *
   * @return a set of all companies associated with the current user
   */
  @Operation(summary = "Get all companies associated with the current user", description = "Get a set of all companies associated with the current user")
  @PreAuthorize("hasAnyAuthority('USER')")
  @GetMapping("/current_user_companies")
  public ResponseEntity<Set<Company>> getCurrentUserCompanies() {
    AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.status(HttpStatus.OK).body(companyService.getAllCompaniesByUserId(userDetails.getId()));
  }

  @GetMapping("/cars/{companyId}")
  public ResponseEntity<List<Car>> getCarsBelongingToCompany(@PathVariable Long companyId) {
    logger.info("Getting all cars belonging to company with id {}", companyId);
    return ResponseEntity.ok(companyService.getCarsBelongingToCompany(companyId));
  }
}
