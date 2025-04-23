package no.ntnu.stud.idata2306_project.dto;

import java.util.Date;

import no.ntnu.stud.idata2306_project.model.contact.Address;
import no.ntnu.stud.idata2306_project.model.contact.PhoneNumber;

public class UserCreationDto {
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private String email;
  private PhoneNumber phoneNumber;
  private Address address;
  private Date dateOfBirth;

  public String getUsername() {
    return username;
  }
  public String getPassword() {
    return password;
  }
  public String getFirstName() {
    return firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public String getEmail() {
    return email;
  }
  public PhoneNumber getPhoneNumber() {
    return phoneNumber;
  }
  public Address getAddress() {
    return address;
  }
  public Date getDateOfBirth() {
    return dateOfBirth;
  }
}