package no.ntnu.stud.idata2306_project.model.company;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import no.ntnu.stud.idata2306_project.model.contact.PhoneNumber;

/**
 * Represents a car providing company
 */
@Entity
public class Company {

    @Schema(description = "The id of the company", example = "1")
    @Id
    @NotNull
    private long id;

    @Schema(description = "The name of the company", example = "Company")
    @NotBlank
    private String name;

    //TODO: use address class, add not null?
    @Schema(description = "The address of the company", example = "Borgundveien 14")
    private String address;

    @Schema(description = "The phone number for the company")
    @NotNull
    @OneToOne //TODO: Check if this is correct
    private PhoneNumber phoneNumber;

    public Company() {}

    /**
     * Creates a new company.
     *
     * @param id the company's id
     * @param name the company's name
     * @param address the company's address
     * @param phoneNumber the company's phone number
     */
    public Company(long id, String name, String address, PhoneNumber phoneNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the company's id.
     *
     * @return the company's id.
     */
    public long getId() {
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
    public PhoneNumber getPhoneNumber() {
        return this.phoneNumber;
    }

}
