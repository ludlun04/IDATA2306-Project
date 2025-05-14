package no.ntnu.stud.idata2306project.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import no.ntnu.stud.idata2306project.dto.UserDto;
import no.ntnu.stud.idata2306project.exception.EmailAlreadyInUser;
import no.ntnu.stud.idata2306project.exception.UserNotFoundException;
import no.ntnu.stud.idata2306project.exception.UsernameAlreadyInUser;
import no.ntnu.stud.idata2306project.model.car.Car;
import no.ntnu.stud.idata2306project.model.contact.Address;
import no.ntnu.stud.idata2306project.model.contact.PhoneNumber;
import no.ntnu.stud.idata2306project.model.user.Role;
import no.ntnu.stud.idata2306project.model.user.User;
import no.ntnu.stud.idata2306project.repository.AddressRepository;
import no.ntnu.stud.idata2306project.repository.PhoneNumberRepository;
import no.ntnu.stud.idata2306project.repository.RoleRepository;
import no.ntnu.stud.idata2306project.repository.UserRepository;
import no.ntnu.stud.idata2306project.security.AccessUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for managing users.
 */
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
   * Constructor for UserService.
   *
   * @param userRepository  the user repository
   * @param roleRepository  the role repository
   * @param passwordEncoder the password encoder
   */
  public UserService(
      UserRepository userRepository, 
      RoleRepository roleRepository, 
      PasswordEncoder passwordEncoder, 
      PhoneNumberRepository phoneNumberRepository, 
      AddressRepository addressRepository, 
      CarService carService) {
    this.phoneNumberRepository = phoneNumberRepository;
    this.addressRepository = addressRepository;
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.carService = carService;
  }

  /**
   * Get all users.
   *
   * @return a list of all users
   */
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  /**
   * Get a user by id.
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
   * Add a user.
   *
   * @param user the user to add
   * @param password the password of the user
   * @return the added user
   * @throws UsernameAlreadyInUser if the email is already in use
   */
  public User addUser(User user, String password) throws EmailAlreadyInUser {
    if (password == null || password.isEmpty()) {
      throw new IllegalArgumentException("Password cannot be null or empty");
    }

    Role role = roleRepository.findByName("USER")
        .orElseThrow(() -> new RuntimeException("Role not found"));
    user.addRole(role);

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
   * Check if a user is an admin.
   *
   * @param userId the id of the user
   * @return true if the user is an admin, false otherwise
   */
  public boolean isAdmin(long userId) {
    Optional<User> user = userRepository.findById(userId);
    return user.map(value -> value.getRoles().stream()
        .anyMatch(role -> role.getName().equals("ADMIN")))
        .orElse(false);
  }

  /**
   * Returns a user from a dto, minus password.
   *
   * @param userDto the user to add
   * @return the added user
   * @throws UsernameAlreadyInUser if the email is already in use
   */
  public User constructUserFromDto(UserDto userDto) {
    if (userDto.getAddress() == null) {
      throw new IllegalArgumentException("Address cannot be null");
    }
    if (userDto.getEmail() == null) {
      throw new IllegalArgumentException("Email cannot be null");
    }
    if (userDto.getPhoneNumber() == null) {
      throw new IllegalArgumentException("Phone number cannot be null");
    }
    if (userDto.getFirstName() == null) {
      throw new IllegalArgumentException("First name cannot be null");
    }
    if (userDto.getLastName() == null) {
      throw new IllegalArgumentException("Last name cannot be null");
    }

    User user = new User();
    user.setFirstname(userDto.getFirstName());
    user.setLastName(userDto.getLastName());
    user.setEmail(userDto.getEmail());
    user.setPhoneNumber(userDto.getPhoneNumber());
    user.setAddress(userDto.getAddress());
    user.setDateOfBirth(userDto.getDateOfBirth());

    return user;
  }

  /**
   * Update a user.
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

  /**
   * Add a car to a user's favorites.
   *
   * @param user the user
   * @param car  the car to add
   */
  public void addFavoriteToUser(User user, Car car) {
    user.addFavorite(car);
    userRepository.save(user);
  }

  /**
   * Set a car as favorite for a user.
   *
   * @param user      the user
   * @param carId     the id of the car
   * @param isFavorite true if the car is a favorite, false otherwise
   * @return true if the car is a favorite, false otherwise
   */
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

  /**
   * Returns user favorites within a list.
   *
   * @param userId the id of the user
   * @param cars   the list of cars
   */
  public List<Car> getUserFavorites(long userId, List<Car> cars) {
    User user = getUserById(userId);
    List<Car> favorites = user.getFavorites();
    Set<Car> favoritesSet = new HashSet<>();
    for (Car car : cars) {
      if (favorites.contains(car)) {
        favoritesSet.add(car);
      }
    }

    return List.copyOf(favoritesSet);
  }

  /**
   * Overwrites values of a user if different from the given userDto.
   *
   * @param userid the id of the user
   * @param userDto the user to update
   * @param performer UserDetails for user performing the update
   * @throws UserNotFoundException if the user is not found
   */
  public void updateUser(long userid, UserDto userDto, AccessUserDetails performer) 
      throws UserNotFoundException {
    Optional<User> userOptional = userRepository.findById(userid);
    if (userOptional.isPresent()) {
      User user = userOptional.get();

      if (userDto.getFirstName() != null && !userDto.getFirstName().isEmpty()) {
        user.setFirstname(userDto.getFirstName());
      }
      if (userDto.getLastName() != null && !userDto.getLastName().isEmpty()) {
        user.setLastName(userDto.getLastName());
      }
      if (userDto.getEmail() != null && !userDto.getEmail().isEmpty()) {
        user.setEmail(userDto.getEmail());
      }
      if (userDto.getDateOfBirth() != null) {
        user.setDateOfBirth(userDto.getDateOfBirth());
      }

      
      if (userDto.getPhoneNumber() != null) {
        PhoneNumber userOhoneNumber = user.getPhoneNumber();
        PhoneNumber dtoPhoneNumber = userDto.getPhoneNumber();

        if (dtoPhoneNumber.getNumber() != null && !dtoPhoneNumber.getNumber().isEmpty()) {
          userOhoneNumber.setNumber(dtoPhoneNumber.getNumber());
        }
        if (dtoPhoneNumber.getCountryCode() != null && !dtoPhoneNumber.getCountryCode().isEmpty()) {
          userOhoneNumber.setCountryCode(dtoPhoneNumber.getCountryCode());
        }
        
        phoneNumberRepository.save(userOhoneNumber);
      }

      if (userDto.getAddress() != null) {
        Address userAddress = user.getAddress();
        Address dtoAddress = userDto.getAddress();

        if (dtoAddress.getStreetAddress() != null && !dtoAddress.getStreetAddress().isEmpty()) {
          userAddress.setStreetAddress(dtoAddress.getStreetAddress());
        }

        if (dtoAddress.getCountry() != null && !dtoAddress.getCountry().isEmpty()) {
          userAddress.setCountry(dtoAddress.getCountry());
        }

        if (dtoAddress.getZipCode() != null && !dtoAddress.getZipCode().isEmpty()) {
          userAddress.setZipCode(dtoAddress.getZipCode());
        }

        this.addressRepository.save(userAddress);
      }

      // Update roles if the performer of the action isn't the user being updated
      if (userDto.getRoles() != null && !performer.getId().equals(user.getId())) {
        HashSet<Role> roles = new HashSet<>();
        for (String roleName : userDto.getRoles()) {
          this.logger.info("Updated user {}", roleName);
          Role role = roleRepository.findByName(roleName)
              .orElseThrow(() -> new RuntimeException("Role not found"));
          roles.add(role);
        }

        user.setRoles(roles);
      }

      userRepository.save(user);
    } else {
      throw new UserNotFoundException("User with id " + userid + " not found");
    }
  }
}
