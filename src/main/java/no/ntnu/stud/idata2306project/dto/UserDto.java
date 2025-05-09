package no.ntnu.stud.idata2306project.dto;

import java.util.Date;
import java.util.List;

import no.ntnu.stud.idata2306project.model.contact.Address;
import no.ntnu.stud.idata2306project.model.contact.PhoneNumber;

public class UserDto {
  private String password;
  private String firstName;
  private String lastName;
  private String email;
  private PhoneNumber phoneNumber;
  private Address address;
  private Date dateOfBirth;
  private List<String> roles;

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
  public List<String> getRoles() {
    return roles;
  }
}