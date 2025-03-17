package no.ntnu.stud.idata2306_project.model.car;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents a feature.
 */
@Entity
public class Feature {

  @Schema(description = "The id of the feature", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "feature_id", nullable = false, updatable = false)
  private long id;

  @Schema(description = "The name of the feature", example = "Parking camera")
  @NotBlank
  @Column(name = "feature_name", nullable = false)
  String name;

  /**
   * Creates a new feature.
   */
  public Feature() {}

  /**
   * Creates a new feature.
   *
   * @param id   the feature's id
   * @param name the feature's name
   */
  public Feature(long id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * Returns the feature's id.
   *
   * @return the feature's id
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Returns the feature's name.
   *
   * @return the feature's name
   */
  public String getName() {
    return this.name;
  }
}
