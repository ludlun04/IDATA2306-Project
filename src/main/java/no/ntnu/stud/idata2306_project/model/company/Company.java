package no.ntnu.stud.idata2306_project.model.company;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a car providing company
 */
@Entity
public class Company {

    @Schema(description = "The id of the company", example = "1")
    @Id
    @NotNull
    private int id;

    @Schema(description = "The name of the company", example = "Company")
    @NotBlank
    private String name;

    @Schema(description = "The address of the company", example = "Borgundveien 14")
    private String address;

    @Schema(description = "The phone number for the company", example = "12345678")
    @NotNull
    private int phoneNumber;

    @Schema(description = "The country code for the phone number", example = "47")
    @NotNull
    private int phoneNumberCountryCode;

    public Company() {}

    /**
     * Creates a new company.
     *
     * @param id the company's id
     * @param name the company's name
     * @param address the company's address
     * @param phoneNumber the company's phone number
     * @param phoneNumberCountryCode the company's phone number country code
     */
    public Company(int id, String name, String address, int phoneNumber, int phoneNumberCountryCode) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (phoneNumber < 0 || phoneNumber > 99999999) {
            throw new IllegalArgumentException("Phone number cannot be negative");
        }
        if (phoneNumberCountryCode < 0 || phoneNumberCountryCode > 999) {
            throw new IllegalArgumentException("Phone number country code cannot be negative");
        }
        if (address != null && address.isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }


        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.phoneNumberCountryCode = phoneNumberCountryCode;
    }

    /**
     * Returns the company's id.
     *
     * @return the company's id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the company's name.
     *
     * @return the company's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the company's address.
     *
     * @return the company's address.
     */
    public String getAddress() {
        if (address != null) {
            return this.address;
        } else {
            return "";
        }
    }

    /**
     * Returns the company's phone number.
     *
     * @return the company's phone number.
     */
    public int getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Returns the company's phone number country code.
     *
     * @return the company's phone number country code.
     */
    public int getPhoneNumberCountryCode() {
        return this.phoneNumberCountryCode;
    }
}
