package no.ntnu.stud.idata2306project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Set;
import no.ntnu.stud.idata2306project.model.car.Car;
import no.ntnu.stud.idata2306project.model.company.Company;
import no.ntnu.stud.idata2306project.model.contact.Address;
import no.ntnu.stud.idata2306project.model.contact.PhoneNumber;
import no.ntnu.stud.idata2306project.model.user.User;
import no.ntnu.stud.idata2306project.repository.AddressRepository;
import no.ntnu.stud.idata2306project.repository.PhoneNumberRepository;
import no.ntnu.stud.idata2306project.security.AccessUserDetails;
import no.ntnu.stud.idata2306project.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Represents a controller for companies.
 *
 * <p>Contains the following endpoints:
 * <ul>
 *  <li> Get all companies
 *  <li> Get a company by its id
 *  <li> Update a company
 *  <li> Add a new company
 *  <li> Delete a company by its id
 *  <li> Add a user to a company
 *  <li> Remove a user from a company
 *  <li> Get all users in a company
 *  <li> Get all companies used in cars
 *  <li> Get companies associated with the current user
 *  <li> Get cars belonging to a company
 * </ul>
 */
@Tag(name = "Companies", description = "Endpoints for managing companies")
@RestController
@RequestMapping("/company")
public class CompanyController {

  private final CompanyService companyService;

  private final Logger logger = LoggerFactory.getLogger(CompanyController.class);

  /**
   * Creates a new CompanyController.
   *
   * @param companyService        the service to use
   */
  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  }

  /**
   * Get all companies. Can only be accessed by admin users.
   *
   * @return a list of all companies
   */
  @Operation(
      summary = "Get all companies",
      description = "Get a list of all companies. Can only be accessed by admin users."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of companies"),
      @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping()
  public ResponseEntity<List<Company>> getCompanies() {
    this.logger.info("Getting companies");
    return ResponseEntity.ok(this.companyService.getCompanies());
  }

  /**
   * Get a company by its id. Can be accessed by admin users and users associated with the company.
   *
   * @param id          the id of the company to get
   * @param userDetails the user details of the current user
   * @return the company that was found
   */
  @Operation(
      summary = "Get a company",
      description = "Get a company by id. Can be accessed by admin users"
          + "and users associated with the company."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Company that was found"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "404", description = "Company not found")
  })
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<Company> getCompany(
      @Parameter(name = "id", description = "The id of the company to get", required = true)
      @PathVariable long id,
      @Parameter(name = "userDetails",
          description = "The user details of the current user", required = true)
      @AuthenticationPrincipal AccessUserDetails userDetails
  ) {
    boolean isAdmin = userDetails.getAuthorities().stream()
        .anyMatch(predicate ->
            predicate.getAuthority().equals("ADMIN"));

    Company company = companyService.getCompanyById(id);
    if (company == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    boolean isUserInCompany = company.getUsers()
            .stream().anyMatch(user -> user.getId().equals(userDetails.getId()));

    if (isAdmin || isUserInCompany) {
      this.logger.info("Company found with id {}", id);
      return ResponseEntity.status(HttpStatus.OK).body(company);
    } else {
      this.logger.error("User {} is not authorized to get company with id {}",
          userDetails.getId(), id);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
  }

  /**
   * Update a company. Can be accessed by admin users and users associated with the company.
   *
   * @param company     the company to update
   * @param userDetails the user details of the current user
   * @return the company that was updated
   */
  @Operation(
      summary = "Update a company",
      description = "Update a company. Can be accessed by admin users"
          + "and users associated with the company."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Company that was updated"),
      @ApiResponse(responseCode = "404", description = "Company not found"),
      @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  @PutMapping("/update")
  public ResponseEntity<Company> updateCompany(
      @Parameter(name = "company", description = "The company to update", required = true)
      @RequestBody Company company,
      @Parameter(name = "userDetails",
          description = "The user details of the current user", required = true)
      @AuthenticationPrincipal AccessUserDetails userDetails
  ) {
    this.logger.info("Updating company with id {}", company.getId());
    Company updatedCompany = companyService.getCompanyById(company.getId());
    boolean isAdmin = userDetails.isAdmin();
    boolean isUserInCompany = companyService.isUserInCompany(userDetails.getId(), company.getId());

    if (isAdmin || isUserInCompany) {
      if (updatedCompany != null) {
        this.companyService.updateCompany(company, updatedCompany);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCompany);
      } else {
        this.logger.error("Company not found with id {}", company.getId());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }
    } else {
      this.logger.error("User {} is not authorized to update company with id {}",
          userDetails.getId(), company.getId());
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
  }

  /**
   * Add a new company. Can only be accessed by admin users.
   *
   * @param company the company to add
   * @return the company that was added
   */
  @Operation(
      summary = "Add a company",
      description = "Add a new company. Can only be accessed by admin users."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Company that was added"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
  })
  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping()
  public ResponseEntity<Long> addCompany(
      @Parameter(name = "company", description = "The company to add", required = true)
      @RequestBody Company company
  ) {
    this.logger.info("Adding company {}", company.getName());
    companyService.addCompany(company);
    this.logger.info("Company added with id {}", company.getId());
    return ResponseEntity.status(HttpStatus.CREATED).body(company.getId());
  }

  /**
   * Delete a company by its id. Can only be accessed by admin users.
   *
   * @param id the id of the company to delete
   * @return the company that was deleted
   */
  @Operation(
      summary = "Delete a company",
      description = "Delete a company by id. Can only be accessed by admin users."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Company that was deleted"),
      @ApiResponse(responseCode = "404", description = "Company not found")
  })
  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Long> deleteCompany(
      @Parameter(name = "id", description = "The id of the company to delete", required = true)
      @PathVariable Long id
  ) {
    this.logger.info("Deleting company with id {}", id);
    companyService.deleteCompanyById(id);
    this.logger.info("Company deleted with id {}", id);
    return ResponseEntity.status(HttpStatus.OK).body(id);
  }


  /**
   * Adds a user to a company. Can only be accessed by admin users.
   *
   * @param companyId the id of the company to add the user to
   * @param userId    the id of the user to add to the company
   * @return a response entity with a message stating if the user was added to the company
   */
  @Operation(
      summary = "Add a user to a company",
      description = "Add a user to a company. Can only be accessed by admin users."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User was added to the company"),
      @ApiResponse(responseCode = "404", description = "Company or user not found")
  })
  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("/{companyId}/user/{userId}")
  public ResponseEntity<String> adduserToCompany(
      @Parameter(name = "companyId",
          description = "The id of the company to add the user to", required = true)
      @PathVariable int companyId,
      @Parameter(name = "userId",
          description = "The id of the user to add to the company", required = true)
      @PathVariable int userId
  ) {
    this.logger.info("Adding user {} to company {}", userId, companyId);
    companyService.addUserToCompany(userId, companyId);
    this.logger.info("User {} added to company {}", userId, companyId);
    return ResponseEntity.status(HttpStatus.OK).body("User was added to the company");
  }

  /**
   * Removes a user from a company. Can only be accessed by admin users.
   *
   * @param companyId the id of the company to remove the user from
   * @param userId    the id of the user to remove from the company
   * @return a response entity with a message stating if the user was removed from the company
   */
  @Operation(
      summary = "Remove a user from a company",
      description = "Remove a user from a company. Can only be accessed by admin users."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User was removed from the company"),
      @ApiResponse(responseCode = "404", description = "Company or user not found")
  })
  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{companyId}/user/{userId}")
  public ResponseEntity<String> removeUserFromCompany(
      @Parameter(name = "companyId",
          description = "The id of the company to remove the user from", required = true)
      @PathVariable int companyId,
      @Parameter(name = "userId",
          description = "The id of the user to remove from the company", required = true)
      @PathVariable int userId
  ) {
    this.logger.info("Removing user {} from company {}", userId, companyId);
    companyService.removeUserFromCompany(userId, companyId);
    this.logger.info("User {} removed from company {}", userId, companyId);
    return ResponseEntity.status(HttpStatus.OK).body("User was removed from the company");
  }


  /**
   * Get all users in a company. Can only be accessed by admin users.
   *
   * @param companyId the id of the company to get users from
   * @return a response entity with a set of users in the company
   */
  @Operation(
      summary = "Get all users in a company",
      description = "Get all users in a company"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of users in the company"),
      @ApiResponse(responseCode = "404", description = "Company not found")
  })
  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/{companyId}/users")
  public ResponseEntity<Set<User>> getUsersInCompany(
      @Parameter(name = "companyId",
          description = "The id of the company to get users from", required = true)
      @PathVariable Long companyId
  ) {
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
  @Operation(
      summary = "Get all companies used in cars",
      description = "Get a set of all companies used in cars"
  )
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
   * Get all companies associated with the current user.
   *
   * @return a set of all companies associated with the current user
   */
  @Operation(
      summary = "Get all companies associated with the current user",
      description = "Get a set of all companies associated with the current user"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Set of companies associated with the current user"),
      @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PreAuthorize("hasAnyAuthority('USER')")
  @GetMapping("/current_user_companies")
  public ResponseEntity<Set<Company>> getCurrentUserCompanies() {
    AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder
        .getContext().getAuthentication().getPrincipal();
    return ResponseEntity.status(HttpStatus.OK)
        .body(companyService.getAllCompaniesByUserId(userDetails.getId()));
  }

  /**
   * Get all cars belonging to a company.
   *
   * @param companyId the id of the company to get cars from
   * @return a list of all cars belonging to the company
   */
  @Operation(
      summary = "Get all cars belonging to a company",
      description = "Get a list of all cars belonging to a company"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of cars belonging to the company"),
      @ApiResponse(responseCode = "404", description = "Company not found")
  })
  @GetMapping("/{companyId}/cars")
  public ResponseEntity<List<Car>> getCarsBelongingToCompany(@PathVariable Long companyId) {
    logger.info("Getting all cars belonging to company with id {}", companyId);
    return ResponseEntity.ok(companyService.getCarsBelongingToCompany(companyId));
  }
}
