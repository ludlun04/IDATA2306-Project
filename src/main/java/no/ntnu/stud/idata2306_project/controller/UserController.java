package no.ntnu.stud.idata2306_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;

import no.ntnu.stud.idata2306_project.model.user.User;
import no.ntnu.stud.idata2306_project.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Operation(summary = "Get all users", description = "Get a list of all users")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of users")
  })
  @GetMapping()
  public ResponseEntity<List<User>> getUsers() {
    return ResponseEntity.ok(userRepository.findAll());
  }


  @Operation(summary = "Get a user", description = "Get a user by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User that was found"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable long id) {
    User user = userRepository.findById(id).orElse(null);
    return ResponseEntity.status(user == null ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(user);
  }

  @Operation(summary = "Get users by name", description = "Get a list of users containing the given name")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of users")
  })
  @GetMapping("/search/{name}")
  public ResponseEntity<List<User>> getUsersByName(@PathVariable String name) {
    return ResponseEntity.ok(userRepository.findUsersByUsernameLike(name));
  }

  @Operation(summary = "Add a user", description = "Add a new user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User that was added")
  })
  @GetMapping("/add")
  public ResponseEntity<User> addUser(User user) {
      User newUser = userRepository.save(user);
      return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
  }

  @Operation(summary = "Delete a user", description = "Delete a user by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User that was deleted"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @GetMapping("/delete/{id}")
  public ResponseEntity<User> deleteUser(@PathVariable long id) {
      User user = userRepository.findById(id).orElse(null);
      if (user != null) {
      userRepository.delete(user);
      }
      return ResponseEntity.status(user == null ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(user);
  }
}
