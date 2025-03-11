package no.ntnu.stud.idata2306_project.model.car;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a car model.
 */
public class CarModel {

  @Schema(description = "The id of the car model", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "model_id", nullable = false, updatable = false)
  long id;

  @Schema(description = "The name of the car model", example = "Corolla")
  @NotNull
  @NotEmpty
  @NotBlank
  @Column(name = "model_name", nullable = false)
  String name;

  @Schema(description = "The brand of the car model", example = "Toyota")
  @NotBlank
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
  public CarModel(long id, String name, CarBrand brand) {
    this.id = id;
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
