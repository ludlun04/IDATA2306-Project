package no.ntnu.stud.idata2306_project.tools;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import no.ntnu.stud.idata2306_project.model.car.Car;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import no.ntnu.stud.idata2306_project.enums.Gender;
import no.ntnu.stud.idata2306_project.model.contact.Address;
import no.ntnu.stud.idata2306_project.model.contact.PhoneNumber;
import no.ntnu.stud.idata2306_project.model.user.Role;
import no.ntnu.stud.idata2306_project.model.user.User;
import no.ntnu.stud.idata2306_project.repository.AddressRepository;
import no.ntnu.stud.idata2306_project.repository.PhoneNumberRepository;
import no.ntnu.stud.idata2306_project.repository.RoleRepository;
import no.ntnu.stud.idata2306_project.service.UserService;

@Component
public class UserInitializer {
  private final UserService userService;
  private final AddressRepository addressRepository;
  private final PhoneNumberRepository phoneNumberRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  private static final String DEFAULT_PASSWORD = "yes";

  public UserInitializer(UserService userService,
      AddressRepository addressRepository,
      PhoneNumberRepository phoneNumberRepository,
      RoleRepository roleRepository,
      PasswordEncoder passwordEncoder) {
    super();
    this.userService = userService;
    this.addressRepository = addressRepository;
    this.phoneNumberRepository = phoneNumberRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public void initializeUsers() {
    Role adminRole = new Role("ADMIN");
    Role userRole = new Role("USER");
    roleRepository.save(adminRole);
    roleRepository.save(userRole);
    
    
    Address address = new Address("6009", "Norway", "Apple Road 2");
    addressRepository.save(address);
    
    PhoneNumber phoneNumber = new PhoneNumber("+47", "12345678");
    phoneNumberRepository.save(phoneNumber);

    // Create admin User
    User admin = new User();
    admin.setRoles(new HashSet<>(List.of(userRole, adminRole)));
    admin.setEmail("admin@driveio.no");
    admin.setFirstname("admin");
    admin.setLastName("admin");
    admin.setUsername("admin");
    admin.setAddress(address);
    admin.setGender(Gender.MALE);
    admin.setPassword(DEFAULT_PASSWORD);
    admin.setPhoneNumber(phoneNumber);
    admin.setDateOfBirth(new Date(System.currentTimeMillis() - 108273460));

    // Create normal User
    User user = new User();
    user.setRoles(new HashSet<>(List.of(userRole)));
    user.setUsername("user");
    user.setFirstname("user");
    user.setLastName("user");
    user.setAddress(address);
    user.setGender(Gender.FEMALE);
    user.setPassword(DEFAULT_PASSWORD);
    user.setPhoneNumber(phoneNumber);
    user.setDateOfBirth(new Date(System.currentTimeMillis() - 108273460));
    user.setEmail("user@emailprovider.domain");

    // Save the users to the database
    userService.addUser(admin);
    userService.addUser(user);
  }
}
