package no.ntnu.stud.idata2306project.dto;

import java.util.Date;
import java.util.List;
import no.ntnu.stud.idata2306project.model.contact.Address;
import no.ntnu.stud.idata2306project.model.contact.PhoneNumber;

/**
 * Represents a user data transfer object (DTO).
 *
 * <p>This class is used to transfer user data between the frontend and backend.
 */
public class UserDto {
  private String password;
  private String firstName;
  private String lastName;
  private String email;
  private PhoneNumber phoneNumber;
  private Address address;
  private Date dateOfBirth;
  private List<String> roles;

  /**
   * Gets the password of the user.
   *
   * @return the password of the user
   */
  public String getPassword() {
    return password;
  }

  /**
   * Gets the first name of the user.
   *
   * @return the first name of the user
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Gets the last name of the user.
   *
   * @return the last name of the user
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Gets the email of the user.
   *
   * @return the email of the user
   */
  public String getEmail() {
    return email;
  }

  /**
   * Gets the phone number of the user.
   *
   * @return the phone number of the user
   */
  public PhoneNumber getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Gets the address of the user.
   *
   * @return the address of the user
   */
  public Address getAddress() {
    return address;
  }

  /**
   * Gets the date of birth of the user.
   *
   * @return the date of birth of the user
   */
  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * Gets the roles of the user.
   *
   * @return the roles of the user
   */
  public List<String> getRoles() {
    return roles;
  }
}