package no.ntnu.stud.idata2306project.tools;

import java.util.Date;
import no.ntnu.stud.idata2306project.enums.Gender;
import no.ntnu.stud.idata2306project.model.contact.Address;
import no.ntnu.stud.idata2306project.model.contact.PhoneNumber;
import no.ntnu.stud.idata2306project.model.user.Role;
import no.ntnu.stud.idata2306project.model.user.User;
import no.ntnu.stud.idata2306project.repository.AddressRepository;
import no.ntnu.stud.idata2306project.repository.PhoneNumberRepository;
import no.ntnu.stud.idata2306project.repository.RoleRepository;
import no.ntnu.stud.idata2306project.service.UserService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Class that initializes the database with some default users.
 */
@Component
public class UserInitializer {
  private final UserService userService;
  private final AddressRepository addressRepository;
  private final PhoneNumberRepository phoneNumberRepository;
  private final RoleRepository roleRepository;
  private final Environment environment;

  /**
   * Constructor for UserInitializer.
   *
   * @param userService users
   * @param addressRepository address
   * @param phoneNumberRepository phone
   * @param roleRepository role
   */
  public UserInitializer(UserService userService,
      AddressRepository addressRepository,
      PhoneNumberRepository phoneNumberRepository,
      RoleRepository roleRepository,
      Environment environment) {
    this.environment = environment;
    this.userService = userService;
    this.addressRepository = addressRepository;
    this.phoneNumberRepository = phoneNumberRepository;
    this.roleRepository = roleRepository;
  }

  /**
   * Initializes the database with some default users.
   */
  public void initializeUsers() {
    
    Address address = new Address("6009", "Norway", "Apple Road 2");
    addressRepository.save(address);
    
    PhoneNumber phoneNumber = new PhoneNumber("+47", "12345678");
    phoneNumberRepository.save(phoneNumber);
    
    Role adminRole = roleRepository.save(new Role("ADMIN"));
    Role userRole = roleRepository.save(new Role("USER"));

    // Create admin User
    User admin = new User();
    admin.addRole(userRole);
    admin.addRole(adminRole);
    admin.setEmail(environment.getProperty("ADMIN_USER_EMAIL"));
    admin.setFirstname("admin");
    admin.setLastName("admin");
    admin.setAddress(address);
    admin.setGender(Gender.MALE);
    admin.setPhoneNumber(phoneNumber);
    admin.setDateOfBirth(new Date(System.currentTimeMillis() - 108273460));

    // Create normal User
    User user = new User();
    user.addRole(userRole);
    user.setEmail(environment.getProperty("REGULAR_USER_EMAIL"));
    user.setFirstname("user");
    user.setLastName("user");
    user.setAddress(address);
    user.setGender(Gender.FEMALE);
    user.setPhoneNumber(phoneNumber);
    user.setDateOfBirth(new Date(System.currentTimeMillis() - 108273460));

    // Create user with company
    User companyUser = new User();
    companyUser.addRole(userRole);
    companyUser.setFirstname("company");
    companyUser.setLastName("user");
    companyUser.setAddress(address);
    companyUser.setGender(Gender.MALE);
    companyUser.setPhoneNumber(phoneNumber);
    companyUser.setDateOfBirth(new Date(System.currentTimeMillis() - 108273460));
    companyUser.setEmail(environment.getProperty("COMPANY_USER_EMAIL"));

    
    // Save the users to the database
    userService.addUser(admin, environment.getProperty("ADMIN_USER_PASSWORD"));
    userService.addUser(user, environment.getProperty("REGULAR_USER_PASSWORD"));
    userService.addUser(companyUser, environment.getProperty("COMPANY_USER_PASSWORD"));
  }
}
