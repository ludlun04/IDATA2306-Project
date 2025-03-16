package no.ntnu.stud.idata2306_project.model.car;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents an addon.
 */
@Entity
public class Addon {
  @Schema(description = "The id of the addon", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "addon_id", nullable = false, updatable = false)
  private long id;

  @Schema(description = "The name of the addon", example = "Baby seat")
  @NotBlank
  @Column(name = "addon_name", nullable = false)
  String name;

  /**
   * Creates a new addon.
   */
  public Addon() {}

  /**
   * Creates a new addon.
   *
   * @param id the addon's id
   * @param name the addon's name
   */
  public Addon(long id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * Returns the addon's id.
   *
   * @return the addon's id
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Returns the addon's name.
   *
   * @return the addon's name
   */
  public String getName() {
    return this.name;
  }
}
