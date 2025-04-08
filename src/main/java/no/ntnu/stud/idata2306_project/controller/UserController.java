package no.ntnu.stud.idata2306_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import no.ntnu.stud.idata2306_project.exception.UserNotFoundException;
import no.ntnu.stud.idata2306_project.model.user.User;
import no.ntnu.stud.idata2306_project.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @Operation(summary = "Get users by name", description = "Get a list of users containing the given name")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of users")
  })
  @GetMapping("/search/{name}")
  public ResponseEntity<List<User>> getUsersByName(@PathVariable String name) {
    this.logger.info("Searching for users with name {}", name);
    return ResponseEntity.ok(userService.getUsersByName(name));
  }

  @Operation(summary = "Add a user", description = "Add a new user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User that was added")
  })
  @GetMapping("/add")
  public ResponseEntity<String> addUser(@RequestBody User user) {
    this.logger.info("Adding user {}", user.getUsername());

    if (user.getAddress() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Address is required");
    }
    if (user.getUsername() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is required");
    }
    if (user.getPassword() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password is required");
    }
    if (user.getEmail() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required");
    }
    if (user.getPhoneNumber() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone number is required");
    }
    if (user.getFirstName() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("First name is required");
    }
    if (user.getLastName() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Last name is required");
    }
    if (user.getPassword() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password is required");
    }

    User newUser = userService.addUser(user);
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
  @GetMapping("/delete/{id}")
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
}
