package no.ntnu.stud.idata2306project.model.car;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents an addon.
 */
@Entity
public class Addon {
  @Schema(description = "The id of the addon", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Schema(description = "The name of the addon", example = "Baby seat")
  @NotBlank
  @Column(name = "addon_name", nullable = false)
  String name;

  @Schema(description = "The price of the addon, in NOK", example = "100")
  @Column(name = "addon_price", nullable = false)
  int price;

  /**
   * Creates a new addon.
   */
  public Addon() {}

  /**
   * Creates a new addon.
   *
   * @param name the addon's name
   */
  public Addon(String name, int price) {
    this.name = name;
    this.price = price;
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

  /**
   * Returns the addon's price.
   *
   * @return the addon's price
   */
  public int getPrice() {
    return price;
  }
}
