package no.ntnu.stud.idata2306_project.model.car;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents a transmission type.
 */
@Entity
public class TransmissionType {
  @Schema(description = "The id of the transmission type", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "transmission_id", nullable = false, updatable = false)
  private long id;

  @Schema(description = "The name of the transmission type", example = "Manual")
  @NotBlank
  @Column(name = "transmission_name", nullable = false)
  String name;

  /**
   * Creates a new transmission
   * type.
   */
  public TransmissionType() {}

  /**
   * Creates a new transmission
   * type.
   *
   * @param id the transmission
   *          type's id
   * @param name the transmission
   *            type's name
   */
  public TransmissionType(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * Returns the transmission
   * type's id.
   *
   * @return the transmission
   * type's id
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Returns the transmission
   * type's name.
   *
   * @return the transmission
   * type's name
   */
  public String getName() {
    return this.name;
  }
}
