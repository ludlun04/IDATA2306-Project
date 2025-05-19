package no.ntnu.stud.idata2306project.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import no.ntnu.stud.idata2306project.enums.Gender;
import no.ntnu.stud.idata2306project.model.car.Car;
import no.ntnu.stud.idata2306project.model.contact.Address;
import no.ntnu.stud.idata2306project.model.contact.PhoneNumber;

/**
 * Represents a user.
 */
@Entity
@Table(name = "app_user")
public class User {
  @Schema(description = "The id of the user", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  @Schema(description = "The password of the user", example = "password")
  @JsonIgnore
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
  PhoneNumber phoneNumber;

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
  @JsonIgnore
  @ManyToMany(fetch = FetchType.EAGER)
  Set<Car> favorites;

  @Schema(description = "The user's roles")
  @ManyToMany(fetch = FetchType.EAGER)
  Set<Role> roles = new HashSet<>();

  /**
   * Default constructor for User.
   */
  public User() {/* */

  }

  /**
   * Returns the user's id.
   *
   * @return the user's id
   */
  public Long getId() {
    return id;
  }

  /**
   * Returns roles associated with the current user.
   *
   * @return A set of roles
   */
  public Set<Role> getRoles() {
    return this.roles;
  }

  /**
   * Sets the roles for the user.
   *
   * @param roles a set of roles to be set as the current roles
   */
  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  /**
   * Adds a role to the user.
   *
   * @param role the role to be added
   */
  public void addRole(Role role) {
    if (this.roles.stream().anyMatch(r -> r.getId() == role.getId())) {
      return;
    }
    this.roles.add(role);
  }

  /**
   * Removes a role from the user.
   *
   * @param role the role to be removed
   */
  public void removeRole(Role role) {
    this.roles.remove(role);
  }


  /**
   * Returns the user's password.
   *
   * @return the user's password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Returns the user's phone number.
   *
   * @return the user's phone number
   */
  public PhoneNumber getPhoneNumber() {
    return this.phoneNumber;
  }

  /**
   * Returns the user's date of birth.
   *
   * @return the user's date of birth
   */
  public Date getDateOfBirth() {
    return this.dateOfBirth;
  }

  /**
   * Returns the user's email.
   *
   * @return the user's email
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Returns the user's gender.
   *
   * @return the user's gender
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
  public Set<Car> getFavorites() {
    return this.favorites;
  }

  /**
   * Sets the user's password.
   *
   * @param password the new password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Sets the user's phone number.
   *
   * @param phoneNumber the new phone number
   */
  public void setPhoneNumber(PhoneNumber phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * Sets the user's date of birth.
   *
   * @param dateOfBirth the new date of birth
   */
  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  /**
   * Sets the user's email.
   *
   * @param email the new email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Sets the users first name.
   *
   * @param firstName the new first name
   */
  public void setFirstname(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Sets the users last name.
   *
   * @param lastName the new last name
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Sets the user's address.
   *
   * @param address the new address
   */
  public void setAddress(Address address) {
    this.address = address;
  }

  /**
   * Sets the user's favorites.
   *
   * @param favorites the new favorites
   */
  public void setFavorites(Set<Car> favorites) {
    this.favorites = favorites;
  }

  /**
   * Adds a car to the user's favorites.
   *
   * @param car the car to be added to the favorites
   */
  public void addFavorite(Car car) {
    if (!favorites.contains(car)) {
      this.favorites.add(car);
    }
  }

  /**
   * Adds cars to the user's favorites.
   *
   * @param cars the cars to be added to the favorites
   */
  public void addFavorites(Car... cars) {
    this.favorites.addAll(Arrays.asList(cars));
  }

  /**
   * Removes a car from the user's favorites.
   *
   * @param car the car to be removed from the favorites
   */
  public void removeFavorite(Car car) {
    this.favorites.remove(car);
  }

  /**
   * Sets the users gender.
   *
   * @param gender the new gender
   */
  public void setGender(Gender gender) {
    this.gender = gender;
  }

}
