package no.ntnu.stud.idata2306_project.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import no.ntnu.stud.idata2306_project.dto.UserDto;
import no.ntnu.stud.idata2306_project.exception.EmailAlreadyInUser;
import no.ntnu.stud.idata2306_project.model.car.Car;
import no.ntnu.stud.idata2306_project.model.contact.Address;
import no.ntnu.stud.idata2306_project.model.contact.PhoneNumber;

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


  /**
   * Overwrites values of a user if different from the given userDto
   * @param userid
   * @param userDto
   * @throws UserNotFoundException
   */
  public void updateUser(long userid, UserDto userDto) throws UserNotFoundException {
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

      if (userDto.getRoles() != null) {
        HashSet<Role> roles = new HashSet<>();
        for (String roleName : userDto.getRoles()) {
          this.logger.info("Updated user {}", roleName);
          Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
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
