package no.ntnu.stud.idata2306_project.model.car;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents a car brand.
 */
@Entity
public class CarBrand {

  @Schema(description = "The id of the car brand", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  @Schema(description = "The name of the car brand", example = "Toyota")
  @NotBlank
  String name;

  /**
   * Creates a new car brand.
   */
  public CarBrand() {}

  /**
   * Creates a new car brand.
   *
   * @param name the car brand's name
   */
  public CarBrand(String name) {
      this.name = name;
  }

  /**
   * Returns the car brand's id.
   *
   * @return the car brand's id
   */
  public long getId() {
      return this.id;
  }

  /**
   * Returns the car brand's name.
   *
   * @return the car brand's name
   */
  public String getName() {
      return this.name;
  }
}
