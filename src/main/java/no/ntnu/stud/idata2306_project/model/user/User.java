package no.ntnu.stud.idata2306_project.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import no.ntnu.stud.idata2306_project.enums.Gender;
import no.ntnu.stud.idata2306_project.model.car.Car;
import no.ntnu.stud.idata2306_project.model.contact.Address;
import no.ntnu.stud.idata2306_project.model.contact.PhoneNumber;

/**
 * User entity class
 */
@Entity
@Table(name = "app_user")
public class User {

  @Schema(description = "The id of the user", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Schema(description = "The username of the user", example = "username")
  @NotBlank
  @Column(unique = true)
  String username;

  @Schema(description = "The password of the user", example = "password")
  @NotBlank
  String password;

  @Schema(description = "The first name of the user", example = "John")
  @NotBlank
  String firstName;

  @Schema(description = "The last name of the user", example = "Doe")
  @NotBlank
  String lastName;

  @Schema(description = "The phone number of the user", example = "12345678")
  @NotNull
  @ManyToOne
  private PhoneNumber phoneNumber;

  @Schema(description = "The address of the user")
  @NotNull
  @ManyToOne
  Address address;

  @Schema(description = "The date of birth of the user", example = "2000-01-01")
  @NotNull
  Date dateOfBirth;

  @Schema(description = "The email of the user", example = "email@email.com")
  @NotBlank
  String email;

  @Schema(description = "The gender of a user")
  Gender gender = Gender.UNIDENTIFIED;

  @Schema(description = "The user's favorite cars")
  @ManyToMany
  private List<Car> favorites;

  @Schema(description = "The user's roles")
  @ManyToMany
  private List<Role> roles;

  public List<Role> getRoles() {
    return roles;
  }

  public void addRole(Role role) {
    if (role == null) {
      throw new IllegalArgumentException("Role cannot be null");
    }

    this.roles.add(role);
  }

  public void removeRole(Role role) throws IllegalArgumentException {
    if (role == null) {
      throw new IllegalArgumentException("Role cannot be null");
    }

    if (this.roles.contains(role)) {
      this.roles.remove(role);
    } else {
      throw new IllegalArgumentException("Role not found");
    }
  }

  /**
   * Returns the user's id.
   *
   * @return the user's id
   */
  public String getPassword() {
    return password;
  }

  /**
   * Returns the user's username.
   *
   * @return the user's username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Returns whether the user's account has expired.
   *
   * @return whether the user's account has expired
   */
  public PhoneNumber getPhoneNumber() {
    return this.phoneNumber;
  }

  /**
   * Returns whether the user's account is locked.
   *
   * @return whether the user's account is locked
   */
  public Date getDateOfBirth() {
    return this.dateOfBirth;
  }

  /**
   * Returns whether the user's credentials have expired.
   *
   * @return whether the user's credentials have expired
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Returns whether the user is enabled.
   *
   * @return whether the user is enabled
   */
  public Gender getGender() {
    return this.gender;
  }

  /**
   * Returns the user's first name.
   *
   * @return the user's first name
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * Returns the user's last name.
   *
   * @return the user's last name
   */
  public String getLastName() {
    return this.lastName;
  }

  /**
   * Returns the user's address.
   *
   * @return the user's address
   */
  public Address getAddress() {
    return this.address;
  }

  /**
   * Returns the user's favorites.
   *
   * @return the user's favorites
   */
  public List<Car> getFavorites() {
    return this.favorites;
  }

  /**
   * Sets the user's username.
   *
   * @param username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Sets the user's password.
   *
   * @param password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Sets the user's phone number.
   *
   * @param phoneNumber
   */
  public void setPhoneNumber(PhoneNumber phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * Sets the user's date of birth.
   *
   * @param dateOfBirth
   */
  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  /**
   * Sets the user's email.
   *
   * @param email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Sets the users first name
   * @param firstName
   */
  public void setFirstname(String firstName) {
    this.firstName = firstName;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public void setFavorites(List<Car> favorites) {
    this.favorites = favorites;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

}
