package no.ntnu.stud.idata2306_project.model.company;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import no.ntnu.stud.idata2306_project.model.car.Car;
import no.ntnu.stud.idata2306_project.model.contact.PhoneNumber;
import no.ntnu.stud.idata2306_project.model.user.User;

/**
 * Represents a car providing company
 */
@Entity
public class Company {

    @Schema(description = "The id of the company", example = "1")
    @Id
    private long id;

    @Schema(description = "The name of the company", example = "Company")
    @NotBlank
    private String name;

    @Schema(description = "The address of the company", example = "Borgundveien 14")
    @NotNull
    private String address;

    @Schema(description = "The phone number for the company")
    @NotNull
    @ManyToOne
    private PhoneNumber phoneNumber;

    @Schema(description = "The list of cars provided by the company")
    @OneToMany
    @JsonIgnore
    private List<Car> cars;

    @Schema(description = "The list of users that are associated with the company")
    @ManyToMany
    @JsonIgnore
    private List<User> users;

    public Company() {}

    /**
     * Creates a new company.
     *
     * @param name the company's name
     * @param address the company's address
     * @param phoneNumber the company's phone number
     */
    public Company(String name, String address, PhoneNumber phoneNumber) {
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
