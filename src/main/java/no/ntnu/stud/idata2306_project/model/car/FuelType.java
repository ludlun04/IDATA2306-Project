package no.ntnu.stud.idata2306_project.model.car;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents a fuel type.
 */
@Entity
public class FuelType {

  @Schema(description = "The id of the fuel type", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "fuel_id", nullable = false, updatable = false)
  private long id;

  @Schema(description = "The name of the fuel type", example = "Petrol")
  @NotBlank
  @Column(name = "fuel_name", nullable = false)
  String name;

  /**
   * Creates a new fuel type.
   */
  public FuelType() {}

  /**
   * Creates a new fuel type.
   *
   * @param id the fuel type's id
   * @param name the fuel type's name
   */
  public FuelType(long id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * Returns the fuel type's id.
   *
   * @return the fuel type's id
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Returns the fuel type's name.
   *
   * @return the fuel type's name
   */
  public String getName() {
    return this.name;
  }
}
