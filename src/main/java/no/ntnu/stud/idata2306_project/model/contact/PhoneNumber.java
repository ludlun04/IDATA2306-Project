package no.ntnu.stud.idata2306_project.model.contact;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents a phone number
 */
@Entity
public class PhoneNumber {
  @Schema(description = "The id of the phone number", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Schema(description = "The country code of the phone number", example = "+47")
  @NotBlank
  String countryCode;

  @Schema(description = "The number part of the phone number", example = "120 002 890")
  @NotBlank
  String number;

  /**
   * Creates a phone number
   */
  public PhoneNumber() {}

  /**
   * Creates a phone number using an id, a country code and a number
   * @param id the id of the phone number
   * @param countryCode the country code of the phone number
   * @param number the number part of the phone number
   */
  public PhoneNumber(String countryCode, String number) {
    this.countryCode = countryCode;
    this.number = number;
  }

  /**
   * Returns the id of the phone number
   * @return the id of the phone number
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the country code
   * @param code the new country code
   */
  public void setCountryCode(String code) {
    this.countryCode = code;
  }

  /**
   * Returns the country code pf the phone number
   * @return the country code of the phone number
   */
  public String getCountryCode() {
    return this.countryCode;
  }

  /**
   * Sets the number part of the phone number
   * @param number the new number part of the phone number
   */
  public void setNumber(String number) {
    this.number = number;
  }

  /**
   * Returns the number part of the phone number
   * @return the number part of the phone number
   */
  public String getNumber() {
    return this.number;
  }
}
