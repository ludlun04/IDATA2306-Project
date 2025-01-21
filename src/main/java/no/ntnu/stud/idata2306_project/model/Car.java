package no.ntnu.stud.idata2306_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a car.
 *
 */
@Entity
public class Car {
  @Id
  @NotNull
  private int id;
  private String name;

  //TODO: company
  //TODO: addons

  public Car() {}

  /**
   * Returns the car's id.
   *
   * @return the car's id
   */
  public int getId() {
    return this.id;
  }

  /**
   * Returns the car's name.
   *
   * @return the car's name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the car's id.
   * @param newid
   */
  public void setId(int newid) {
    this.id = newid;
  }

  /**
   * Sets the car's name. The name cannot be null or empty.
   * @param name
   * @throws IllegalArgumentException if the name is null or empty
   */
  public void setName(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }
    this.name = name;
  }
}
