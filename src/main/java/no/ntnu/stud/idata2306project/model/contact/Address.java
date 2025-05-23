package no.ntnu.stud.idata2306project.model.contact;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

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
  private String zipCode;

  @Schema(description = "the country of the address", example = "Norway")
  @NotBlank
  private String country;

  @Schema(description = "the address", example = "Apple Road 2")
  @NotBlank
  private String streetAddress;

  /**
   * Creates an address.
   */
  public Address() {/* */
  }

  /**
   * Creates an address using a zip code, a country and an address.
   *
   * @param zipCode the zip code of the address
   * @param country the country of the address
   * @param address the address
   */
  public Address(String zipCode, String country, String address) {
    this.zipCode = zipCode;
    this.country = country;
    this.streetAddress = address;
  }

  /**
   * Returns the id of the address.
   *
   * @return the id of the address
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Returns the country of the address.
   *
   * @return the country of the address
   */
  public String getCountry() {
    return this.country;
  }

  /**
   * Returns the street address part of the address.
   *
   * @return the address part of the address
   */
  public String getStreetAddress() {
    return this.streetAddress;
  }

  /**
   * Returns the zip code of the address.
   *
   * @return the zip code of the address
   */
  public String getZipCode() {
    return this.zipCode;
  }

  /**
   * Sets the street address.
   *
   * @param address the new address
   */
  public void setStreetAddress(String address) {
    this.streetAddress = address;
  }

  /**
   * Sets the country.
   *
   * @param country the new country
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * Sets the zip code of the address.
   *
   * @param zipCode the new zip code
   */
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }
}
