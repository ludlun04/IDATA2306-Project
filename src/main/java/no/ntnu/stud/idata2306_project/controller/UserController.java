package no.ntnu.stud.idata2306_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashSet;
import java.util.List;

import java.util.Set;

import jakarta.validation.Valid;
import no.ntnu.stud.idata2306_project.dto.CarFavoriteRequestDto;
import no.ntnu.stud.idata2306_project.dto.UserDto;
import no.ntnu.stud.idata2306_project.exception.UserNotFoundException;
import no.ntnu.stud.idata2306_project.model.car.Car;
import no.ntnu.stud.idata2306_project.model.user.Role;
import no.ntnu.stud.idata2306_project.model.user.User;
import no.ntnu.stud.idata2306_project.security.AccessUserDetails;
import no.ntnu.stud.idata2306_project.service.UserService;

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

@Tag(name = "Users", description = "Endpoints for managing users")
@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  private Logger logger = LoggerFactory.getLogger(UserController.class);

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Operation(summary = "Get all users", description = "Get a list of all users")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of users")
  })
  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping()
  public ResponseEntity<List<User>> getUsers() {
    return ResponseEntity.ok(userService.getUsers());
  }

  @Operation(summary = "Get authenticated user", description = "Get the authenticated user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Authenticated user")
  })
  @PreAuthorize("hasAnyAuthority('USER')")
  @GetMapping("/details")
  public ResponseEntity<User> getAuthenticatedUser() {
    AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    User user = userService.getUserById(userDetails.getId());
    return ResponseEntity.ok(user);
  }

  @Operation(summary = "Get favorites", description = "Get the authenticated user's favorited cars")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of authenticated users favorites")
  })
  @PreAuthorize("hasAnyAuthority('USER')")
  @GetMapping("/favorites")
  public ResponseEntity<List<Car>> getAuthenticatedUserFavorites() {
    AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    User user = userService.getUserById(userDetails.getId());
    return ResponseEntity.ok(user.getFavorites());
  }

  @Operation(summary = "Get favorites from given list", description = "Get the authenticated user's favorited cars among given cars")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of authenticated users favorites among given cars")
  })
  @PreAuthorize("hasAnyAuthority('USER')")
  @PostMapping("/favorites")
  public ResponseEntity<List<Car>> getAuthenticatedUserFavoritesFromList(@RequestBody List<Car> cars) {
    AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    User user = userService.getUserById(userDetails.getId());
    Set<Car> matching = new HashSet<>();

    List<Long> carIds = cars.stream().map(Car::getId).toList();
    for (Car favorite : user.getFavorites()) {
      if (carIds.contains(favorite.getId())) {
        matching.add(favorite);
      }
    }
    return ResponseEntity.ok(matching.stream().toList());
  }

  @Operation(summary = "Set favorite on a given car", description = "Set favorite on a given car to a given status")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Favorite set on car"),
      @ApiResponse(responseCode = "400", description = "Bad request")
  })
  @PreAuthorize("hasAnyAuthority('USER')")
  @PostMapping("/favorites/alter")
  public ResponseEntity<Boolean> setAuthenticatedUserFavorite(@RequestBody @Valid CarFavoriteRequestDto carFavorite) {
    logger.info("Setting favorite on car");

    ResponseEntity<Boolean> badRequest = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
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

  @Operation(summary = "Get roles", description = "Get the authenticated user's roles")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of authenticated users roles")
  })
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  @GetMapping("/roles")
  public ResponseEntity<Set<Role>> getCurrentAuthenticatedUserRoles() {
    AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    User user = userService.getUserById(userDetails.getId());
    return ResponseEntity.ok(user.getRoles());
  }

  @Operation(summary = "Get a user", description = "Get a user by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User that was found"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable long id) {
    try {
      User user = userService.getUserById(id);
      this.logger.info("User found with id {}", id);
      return ResponseEntity.ok().body(user);
    } catch (UserNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @Operation(summary = "Add a user", description = "Add a new user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User that was added")
  })
  @PostMapping("/add")
  public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {
    this.logger.info("Adding user {}", userDto.getFirstName());

    if (userDto.getAddress() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Address is required");
    }
    if (userDto.getEmail() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required");
    }
    if (userDto.getPhoneNumber() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone number is required");
    }
    if (userDto.getFirstName() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("First name is required");
    }
    if (userDto.getLastName() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Last name is required");
    }

    User user = new User();
    user.setFirstname(userDto.getFirstName());
    user.setLastName(userDto.getLastName());
    user.setEmail(userDto.getEmail());
    user.setPhoneNumber(userDto.getPhoneNumber());
    user.setAddress(userDto.getAddress());
    user.setDateOfBirth(userDto.getDateOfBirth());

    User newUser = userService.addUser(user, userDto.getPassword());
    if (newUser == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
    }

    this.logger.info("User added with id {}", newUser.getId());
    return ResponseEntity.status(HttpStatus.CREATED).body("User Created: " + newUser.getId());
  }

  @Operation(summary = "Delete a user", description = "Delete a user by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User that was deleted"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable long id) {
    this.logger.info("Deleting user with id {}", id);
    try {
      this.userService.deleteUser(id);
      this.logger.info("User deleted with id {}", id);
      return ResponseEntity.status(HttpStatus.OK).body("User Removed: " + id);
    } catch (UserNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + id);
    }
  }

  @Operation(summary = "Updates a user", description = "Updates a user by id") 
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "User that was updated"),
    @ApiResponse(responseCode = "404", description = "User not found"),
    @ApiResponse(responseCode = "400", description = "Bad request")
  })
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserDto userDto, @AuthenticationPrincipal AccessUserDetails userDetails) {
    boolean isAdmin = userDetails.getAuthorities().stream()
        .anyMatch(predicate -> predicate.getAuthority().equals("ADMIN"));

    boolean hasSameId = userDetails.getId().equals(id);  
    
    this.logger.info("{} {}", isAdmin, hasSameId);
    if (!isAdmin && !hasSameId) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not admin");
    }

    this.logger.info("Good we cooking");
    try {
      this.userService.updateUser(id, userDto);
    } catch (UserNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + id);
    }

    return ResponseEntity.ok().body("User updated: " + id);
  }
}
