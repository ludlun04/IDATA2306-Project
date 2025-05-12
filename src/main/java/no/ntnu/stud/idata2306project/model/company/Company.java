package no.ntnu.stud.idata2306project.model.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import no.ntnu.stud.idata2306project.model.car.Car;
import no.ntnu.stud.idata2306project.model.contact.Address;
import no.ntnu.stud.idata2306project.model.contact.PhoneNumber;
import no.ntnu.stud.idata2306project.model.user.User;

/**
 * Represents a car providing company.
 */
@Entity
public class Company {

  @Schema(description = "The id of the company", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Schema(description = "The name of the company", example = "Company")
  @NotBlank
  private String name;

  @Schema(description = "The address of the company", example = "Borgundveien 14")
  @NotNull
  @ManyToOne
  private Address address;

  @Schema(description = "The phone number for the company")
  @NotNull
  @ManyToOne
  private PhoneNumber phoneNumber;

  @Schema(description = "The list of cars provided by the company")
  @OneToMany
  @JsonIgnore
  private List<Car> cars;

  @Schema(description = "The list of users that are associated with the company")
  @JsonIgnore
  @ManyToMany(fetch = FetchType.EAGER)
  private Set<User> users = new HashSet<>();

  public Company() {
  }

  /**
   * Creates a new company.
   *
   * @param name        the company's name
   * @param address     the company's address
   * @param phoneNumber the company's phone number
   */
  public Company(String name, Address address, PhoneNumber phoneNumber) {
    this.name = name;
    this.address = address;
    this.phoneNumber = phoneNumber;
  }

  /**
   * Returns the company's id.
   *
   * @return the company's id.
   */
  public long getId() {
    return this.id;
  }

  /**
   * Returns the company's name.
   *
   * @return the company's name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the company's address.
   *
   * @return the company's address.
   */
  public Address getAddress() {
    return this.address;
  }

  /**
   * Sets the company's address.
   *
   * @param address the new address
   */
  public void setAddress(Address address) {
    if (address == null) {
      throw new IllegalArgumentException("Address cannot be null");
    }
    this.address = address;
  }

  /**
   * Sets thje company's phone number
   *
   * @param phoneNumber the new phone number
   */
  public void setPhoneNumber(PhoneNumber phoneNumber) {
    if (phoneNumber == null) {
      throw new IllegalArgumentException("Phone number cannot be null");
    }
    this.phoneNumber = phoneNumber;
  }

  /**
   * Sets the company's name
   *
   * @param name the new name
   */
  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be null or blank");
    }
    this.name = name;
  }

  /**
   * Returns the company's phone number.
   *
   * @return the company's phone number.
   */
  public PhoneNumber getPhoneNumber() {
    return this.phoneNumber;
  }

  /**
   * Add a user to the company.
   *
   * @param user the user to add
   */
  public void addUser(User user) throws IllegalArgumentException {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null");
    }

    this.users.add(user);
  }

  /**
   * Remove a user from the company.
   *
   * @param user the user to remove
   */
  public void removeUser(User user) throws IllegalArgumentException {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null");
    }

    this.users.remove(user);
  }

  /**
   * Returns the list of users associated with the company.
   *
   * @return the list of users associated with the company.
   */
  public Set<User> getUsers() {
    return this.users;
  }
}
