package no.ntnu.stud.idata2306_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a car providing company
 */
@Entity
public class Company {
    @Id
    @NotNull
    private int id;
    @NotNull
    @NotEmpty
    @NotBlank
    private String name;
    private String address;
    @NotNull
    private int phoneNumber;
    @NotNull
    private int phoneNumberCountryCode;

    public Company() {

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
