package no.ntnu.stud.idata2306_project.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import no.ntnu.stud.idata2306_project.exception.UserNotFoundException;
import no.ntnu.stud.idata2306_project.exception.UsernameAlreadyInUser;
import no.ntnu.stud.idata2306_project.model.user.Role;
import no.ntnu.stud.idata2306_project.model.user.User;
import no.ntnu.stud.idata2306_project.repository.RoleRepository;
import no.ntnu.stud.idata2306_project.repository.UserRepository;

@Service
public class UserService {
  UserRepository userRepository;
  RoleRepository roleRepository;
  PasswordEncoder passwordEncoder;

  Logger logger = LoggerFactory.getLogger(UserService.class);

  /**
   * Constructor for UserService
   * @param userRepository the user repository
   * @param roleRepository the role repository
   * @param passwordEncoder the password encoder
   */
  public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  /**
   * Get all users
   * @return a list of all users
   */
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  /**
   * Get a user by id
   * @param id the id of the user
   * @return the user with the given id
   * @throws UserNotFoundException if the user is not found
   */
  public User getUserById(long id) throws UserNotFoundException {
    Optional<User> user = userRepository.findById(id);
    if (user.isEmpty()) {
      throw new UserNotFoundException("User with id " + id + " not found");
    }

    return user.get();
  }

  /**
   * Get a user by username
   * @param username the username of the user
   * @return the user with the given username
   * @throws UserNotFoundException if the user is not found
   */
  public List<User> getUsersByName(String name) {
    return userRepository.findUsersByUsernameLike(name);
  }

  /**
   * Get a user by username
   * @param username the username of the user
   * @return the user with the given username
   * @throws UserNotFoundException if the user is not found
   */
  public User addUser(User user) throws UsernameAlreadyInUser {
    String username = user.getUsername();
    userRepository.findByUsername(username).orElseThrow(() -> new UsernameAlreadyInUser(username));

    userRepository.findByEmail(user.getEmail()).orElseThrow(() -> {
      throw new UsernameAlreadyInUser(user.getEmail());
    });

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    Role role = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role not found"));

    HashSet<Role> roles = new HashSet<>();
    roles.add(role);
    user.setRoles(roles);

    this.logger.info("User with username {} created", username);

    return userRepository.save(user);
  }

  /**
   * Update a user
   * @param id the id of the user
   * @param user the user to update
   * @return the updated user
   * @throws UserNotFoundException if the user is not found
   */
  public void deleteUser(Long id) throws UserNotFoundException {
    Optional<User> user = userRepository.findById(id);
    if (user.isEmpty()) {
      throw new UserNotFoundException("User with id " + id + " not found");
    }

    userRepository.delete(user.get());
  }
}
