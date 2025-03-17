package no.ntnu.stud.idata2306_project.model.contact;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

/**
 * Represents an address.
 */
@Entity
public class Address {
  @Schema(description = "the id of the address", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Schema(description = "the zip code of the address", example = "6009")
  @NotBlank
  String zipCode;

  @Schema(description = "the country of the address", example = "Norway")
  @NotBlank
  String country;

  @Schema(description = "the address", example = "Apple Road 2")
  @NotBlank
  String address;

  /**
   * Creates an address
   */
  public Address() {}

  /**
   * Returns the id of the address
   * @return the id of the address
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Returns the country of the address
   * @return the country of the address
   */
  public String getCountry() {
    return this.country;
  }

  /**
   * Returns the address part of the address
   * @return the address part of the address
   */
  public String getAddress() {
    return this.address;
  }

  /**
   * Returns the zip code of the address
   * @return the zip code of the address
   */
  public String getZipCode() {
    return this.zipCode;
  }

  /**
   * Sets the address
   * @param address the new address
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Sets the country
   * @param country the new country
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * Sets the zip code of the address
   * @param zipCode the new zip code
   */
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }
}
