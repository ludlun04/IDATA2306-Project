package no.ntnu.stud.idata2306_project.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

/**
 * Class representing the different user roles in the system
 */
@Entity
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotBlank
  private String name;

  /**
   * Default constructor for Role
   */
  public Role() {}

  /**
   * Constructor for Role
   * 
   * @param name The name of the role
   */
  public Role(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be null");
    }

    this.name = name;
  }

  /**
   * Returns the id of the role
   * @return the id of the role as a long
   */
  public long getId() {
    return id;
  }

  /**
   * Returns the name of the role
   * @return the name of the role as a string
   */
  public String getName() {
    return name;
  }
}
