package no.ntnu.stud.idata2306_project.model.car;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a car.
 *
 */
@Entity
public class Car {
  @Id
  @NotNull
  private long id;
  @NotNull
  @NotEmpty
  @NotBlank
  private String name;
  
  public Car() {}

  public Car(long id, String name) {
    if (id < 1) {
      throw new IllegalArgumentException("Id must be positive");
    }
    if (name == null) {
      throw new IllegalArgumentException("Name must not be null");
    }
    if (name.isBlank()) {
      throw new IllegalArgumentException("Name must not be blank");
    }

    this.id = id;
    this.name = name;
  }

  /**
   * Returns the car's id.
   *
   * @return the car's id
   */
  public long getId() {
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
   * Sets the car's name.
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }
}
