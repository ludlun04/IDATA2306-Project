package no.ntnu.stud.idata2306_project.service;

import java.util.List;
import java.util.Optional;

import no.ntnu.stud.idata2306_project.exception.EmailAlreadyInUser;
import no.ntnu.stud.idata2306_project.model.car.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import no.ntnu.stud.idata2306_project.exception.UserNotFoundException;
import no.ntnu.stud.idata2306_project.exception.UsernameAlreadyInUser;
import no.ntnu.stud.idata2306_project.model.user.Role;
import no.ntnu.stud.idata2306_project.model.user.User;
import no.ntnu.stud.idata2306_project.repository.AddressRepository;
import no.ntnu.stud.idata2306_project.repository.PhoneNumberRepository;
import no.ntnu.stud.idata2306_project.repository.RoleRepository;
import no.ntnu.stud.idata2306_project.repository.UserRepository;

@Service
public class UserService {
  UserRepository userRepository;
  PhoneNumberRepository phoneNumberRepository;
  AddressRepository addressRepository;
  RoleRepository roleRepository;
  CarService carService;
  PasswordEncoder passwordEncoder;

  Logger logger = LoggerFactory.getLogger(UserService.class);

  /**
   * Constructor for UserService
   *
   * @param userRepository  the user repository
   * @param roleRepository  the role repository
   * @param passwordEncoder the password encoder
   */
  public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, PhoneNumberRepository phoneNumberRepository, AddressRepository addressRepository, CarService carService) {
    this.phoneNumberRepository = phoneNumberRepository;
    this.addressRepository = addressRepository;
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.carService = carService;
  }

  /**
   * Get all users
   *
   * @return a list of all users
   */
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  /**
   * Get a user by id
   *
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
   * Add a user
   * @param user the user to add
   * @param password the password of the user
   * @return the added user
   * @throws UsernameAlreadyInUser if the email is already in use
   */
  public User addUser(User user, String password) throws EmailAlreadyInUser {

    Role role = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role not found"));
    user.addRole(role);

    logger.info(user.getRoles().toString());

    Optional<User> userWithEmail = userRepository.findByEmail(user.getEmail());
    if (userWithEmail.isPresent()) {
      throw new EmailAlreadyInUser(user.getEmail());
    }

    user.setPassword(passwordEncoder.encode(password));

    this.logger.info("User with email {} created", user.getEmail());

    phoneNumberRepository.save(user.getPhoneNumber());
    addressRepository.save(user.getAddress());

    return userRepository.save(user);
  }

  /**
   * Update a user
   *
   * @param id   the id of the user
   * @throws UserNotFoundException if the user is not found
   */
  @PreAuthorize("hasAnyAuthority('ADMIN')")
  public void deleteUser(Long id) throws UserNotFoundException {
    Optional<User> user = userRepository.findById(id);
    if (user.isEmpty()) {
      throw new UserNotFoundException("User with id " + id + " not found");
    }

    userRepository.delete(user.get());
  }

  public void addFavoriteToUser(User user, Car car) {
    user.addFavorite(car);
    userRepository.save(user);
  }

  public boolean setUserFavorite(User user, Long carId, Boolean isFavorite) {
    boolean result;
    Optional<Car> carOptional = carService.getCarById(carId);
    if (carOptional.isPresent()) {
      Car car = carOptional.get();
      if (isFavorite) {
        user.addFavorite(car);
      } else {
        user.removeFavorite(car);
      }
      userRepository.save(user);
      result = isFavorite;
    } else {
      logger.warn("Car with id {} not found", carId);
      throw new IllegalArgumentException("Car with id " + carId + " not found");
    }
    return result;

  }
}
