package no.ntnu.stud.idata2306project.model.car;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a car model.
 */
@Entity
public class CarModel {

  @Schema(description = "The id of the car model", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  @Schema(description = "The name of the car model", example = "Corolla")
  @NotBlank
  @Column(name = "model_name", nullable = false)
  String name;

  @Schema(description = "The brand of the car model", example = "Toyota")
  @NotNull
  @ManyToOne
  CarBrand brand;

  /**
   * Creates a new car model.
   */
  public CarModel() {
  }

  /**
   * Creates a new car model.
   *
   * @param id the car model's id
   * @param name the car model's name
   * @param brand the car model's brand
   */
  public CarModel(String name, CarBrand brand) {
    this.name = name;
    this.brand = brand;
  }

  /**
   * Returns the car model's id.
   *
   * @return the car model's id
   */
  public long getId() {
    return this.id;
  }

  /**
   * Returns the car model's name.
   *
   * @return the car model's name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the car model's brand.
   *
   * @return the car model's brand
   */
  public CarBrand getBrand() {
    return this.brand;
  }
}
