package no.ntnu.stud.idata2306project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import no.ntnu.stud.idata2306project.dto.CarFavoriteRequestDto;
import no.ntnu.stud.idata2306project.dto.UserDto;
import no.ntnu.stud.idata2306project.exception.UserNotFoundException;
import no.ntnu.stud.idata2306project.model.car.Car;
import no.ntnu.stud.idata2306project.model.user.Role;
import no.ntnu.stud.idata2306project.model.user.User;
import no.ntnu.stud.idata2306project.security.AccessUserDetails;
import no.ntnu.stud.idata2306project.service.UserService;
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
 * Represents the controller for managing users.
 *
 * <p>Has the following endpoints:
 * <ul>
 *   <li> Get all users
 *   <li> Get authenticated user
 *   <li> Get authenticated user's favorites
 *   <li> Get authenticated user's favorites from a given list
 *   <li> Set favorite on a given car
 *   <li> Get authenticated user's roles
 *   <li> Get a user by id
 *   <li> Add a new user
 *   <li> Delete a user by id
 *   <li> Update a user by id
 * </ul>
 */
@Tag(name = "Users", description = "Endpoints for managing users")
@RestController
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  /**
   * Create a new UserController.
   *
   * @param userService UserService to use
   */
  public UserController(UserService userService) {
    this.userService = userService;
  }


  /**
   * Get all users.
   *
   * @return List of users
   */
  @Operation(
      summary = "Get all users",
      description = "Get a list of all users. Only accessible by admin"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of users"),
      @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping()
  public ResponseEntity<List<User>> getUsers() {
    return ResponseEntity.ok(userService.getUsers());
  }

  /**
   * Get authenticated user.
   *
   * @return Authenticated user
   */
  @Operation(
      summary = "Get authenticated user",
      description = "Get the authenticated user. Only accessible by user"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Authenticated user"),
      @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PreAuthorize("hasAnyAuthority('USER')")
  @GetMapping("/details")
  public ResponseEntity<User> getAuthenticatedUser() {
    AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder
        .getContext().getAuthentication().getPrincipal();
    User user = userService.getUserById(userDetails.getId());
    return ResponseEntity.ok(user);
  }


  /**
   * Get authenticated user's favorites.
   *
   * @return List of authenticated user's favorites
   */
  @Operation(
      summary = "Get favorites",
      description = "Get a list of the authenticated user's favorite cars. Only accessible by user"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of authenticated users favorites"),
      @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PreAuthorize("hasAnyAuthority('USER')")
  @GetMapping("/favorites")
  public ResponseEntity<List<Car>> getAuthenticatedUserFavorites() {
    AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder
        .getContext().getAuthentication().getPrincipal();
    User user = userService.getUserById(userDetails.getId());
    return ResponseEntity.ok(user.getFavorites());
  }

  /**
   * Get authenticated user's favorites from a given list.
   *
   * @param cars List of cars
   * @return List of authenticated user's favorites from the given list
   */
  @Operation(
      summary = "Get favorites from given list",
      description = "Get a list of the authenticated user's favorite cars among given cars. "
          + "Only accessible by user"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "List of authenticated users favorites among given cars"),
      @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PreAuthorize("hasAnyAuthority('USER')")
  @PostMapping("/favorites")
  public ResponseEntity<List<Car>> getAuthenticatedUserFavoritesFromList(
      @Parameter(description = "List of cars to check against")
      @RequestBody List<Car> cars
  ) {
    AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder
        .getContext().getAuthentication().getPrincipal();

    return ResponseEntity.ok(userService.getUserFavorites(userDetails.getId(), cars));
  }

  /**
   * Set favorite on a given car.
   *
   * @param carFavorite CarFavoriteRequestDto
   * @return ResponseEntity with boolean value that indicates the favorite status
   */
  @Operation(
      summary = "Set favorite on a given car",
      description = "Set favorite on a given car to a given status. Only accessible by user"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Favorite set on car"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
      @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PreAuthorize("hasAnyAuthority('USER')")
  @PostMapping("/favorites/alter")
  public ResponseEntity<Boolean> setAuthenticatedUserFavorite(
      @Parameter(description = "CarFavoriteRequestDto with car id and favorite status")
      @RequestBody @Valid CarFavoriteRequestDto carFavorite
  ) {
    logger.info("Setting favorite on car");

    ResponseEntity<Boolean> badRequest = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder
        .getContext().getAuthentication().getPrincipal();
    User user = userService.getUserById(userDetails.getId());

    Long id = carFavorite.getCarId();
    Boolean isFavorite = carFavorite.isFavorite();

    ResponseEntity<Boolean> toReturn;
    try {
      boolean resultingFavorite = userService.setUserFavorite(user, id, isFavorite);
      toReturn = ResponseEntity.ok(resultingFavorite);
    } catch (IllegalArgumentException e) {
      toReturn = badRequest;
    }
    logger.info("Setting favorite on car with id {} to {}", id, isFavorite);
    return toReturn;
  }

  /**
   * Get authenticated user's roles.
   *
   * @return Set of authenticated user's roles
   */
  @Operation(
      summary = "Get roles",
      description = "Get the authenticated user's roles. Only accessible by user and admin"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of authenticated users roles"),
      @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  @GetMapping("/roles")
  public ResponseEntity<Set<Role>> getCurrentAuthenticatedUserRoles() {
    AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder
        .getContext().getAuthentication().getPrincipal();
    User user = userService.getUserById(userDetails.getId());
    return ResponseEntity.ok(user.getRoles());
  }

  /**
   * Get a user by id.
   *
   * @param id User id
   * @return User with the given id
   */
  @Operation(summary = "Get a user", description = "Get a user by id. Only accessible by admin")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User that was found"),
      @ApiResponse(responseCode = "404", description = "User not found"),
      @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(
      @Parameter(description = "User id to get")
      @PathVariable long id
  ) {
    try {
      User user = userService.getUserById(id);
      this.logger.info("User found with id {}", id);
      return ResponseEntity.ok().body(user);
    } catch (UserNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  /**
   * Add a user.
   *
   * @param userDto UserDto to add
   * @return ResponseEntity with the id of the added user
   */
  @Operation(summary = "Add a user", description = "Add a new user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User that was added"),
      @ApiResponse(responseCode = "400", description = "Bad request")
  })
  @PostMapping("/add")
  public ResponseEntity<String> addUser(
      @Parameter(description = "UserDto to add")
      @RequestBody UserDto userDto
  ) {
    this.logger.info("Adding user {}", userDto.getFirstName());
    User newUser;
    try {
      newUser = userService.addUser(userService.constructUserFromDto(userDto), userDto.getPassword());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    if (newUser == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
    }

    this.logger.info("User added with id {}", newUser.getId());
    return ResponseEntity.status(HttpStatus.CREATED).body("" + newUser.getId());
  }

  /**
   * Delete a user by id.
   *
   * @param id User id to delete
   * @return ResponseEntity with the id of the deleted user
   */
  @Operation(summary = "Delete a user", description = "Delete a user by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User that was deleted"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<String> deleteUser(
      @Parameter(description = "User id to delete")
      @PathVariable long id
  ) {
    this.logger.info("Deleting user with id {}", id);
    try {
      this.userService.deleteUser(id);
      this.logger.info("User deleted with id {}", id);
      return ResponseEntity.status(HttpStatus.OK).body("User Removed: " + id);
    } catch (UserNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + id);
    }
  }

  /**
   * Update a user by id.
   *
   * @param id User id to update
   * @param userDto UserDto containing the new user data
   * @param userDetails Authenticated user details
   * @return ResponseEntity with the id of the updated user
   */
  @Operation(
      summary = "Updates a user",
      description = "Updates a user by id. Only accessible by admin or the user itself"
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "User that was updated"),
    @ApiResponse(responseCode = "404", description = "User not found"),
    @ApiResponse(responseCode = "400", description = "Bad request")
  })
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<String> updateUser(
      @Parameter(description = "User id to update")
      @PathVariable Long id,
      @Parameter(description = "UserDto containing the new user data")
      @RequestBody UserDto userDto,
      @Parameter(description = "Authenticated user details")
      @AuthenticationPrincipal AccessUserDetails userDetails
  ) {
    boolean isAdmin = userService.isAdmin(userDetails.getId());
    boolean hasSameId = userDetails.getId().equals(id);  

    if (!isAdmin && !hasSameId) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not admin");
    }

    try {
      this.userService.updateUser(id, userDto);
    } catch (UserNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + id);
    }

    return ResponseEntity.ok().body("User updated: " + id);
  }
}
