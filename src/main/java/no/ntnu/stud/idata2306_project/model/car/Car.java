package no.ntnu.stud.idata2306_project.model.car;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

/**
 * Represents a car.
 */
@Entity
public class Car {
  @Schema(description = "The id of the car", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Schema(description = "The year of the car", example = "2015")
  @NotNull
  @Positive
  private int year;

  @Schema(description = "The number of seats in the car", example = "5")
  @NotNull
  @Positive
  private int numberOfSeats;

  @Schema(description = "The price per day for renting the car, in NOK", example = "900")
  @NotNull
  @Positive
  private int pricePerDay;

  @Schema(description = "The model of the car")
  @NotNull
  @ManyToOne
  private CarModel model;

  @Schema(description = "The fuel type of the car")
  @NotNull
  @ManyToOne
  private FuelType fuelType;

  @Schema(description = "The transmission type of the car")
  @NotNull
  @ManyToOne
  private TransmissionType transmissionType;

  @Schema(description = "The addons of the car")
  @NotNull
  @ManyToMany
  private List<Addon> addons;

  @Schema(description = "The features of the car")
  @NotNull
  @ManyToMany
  private List<Feature> features;


  public Car() {
  }

  public Car(int year, int numberOfSeats, int pricePerDay, CarModel model,
             FuelType fuelType, TransmissionType transmissionType, List<Addon> addons,
             List<Feature> features) {
    this.year = year;
    this.numberOfSeats = numberOfSeats;
    this.pricePerDay = pricePerDay;
    this.model = model;
    this.fuelType = fuelType;
    this.transmissionType = transmissionType;
    this.addons = addons;
    this.features = features;
  }

  /**
   * Returns the car's id.
   *
   * @return the car's id
   */
  public long getId() {
    return this.id;
  }

  /**
   * Returns the car's year.
   *
   * @return the car's year
   */
  public int getYear() {
    return this.year;
  }

  /**
<<<<<<< HEAD
   * Sets the car's name.
   * @param name
=======
   * Returns the car's number of seats.
   *
   * @return the car's number of seats
   */
  public int getNumberOfSeats() {
    return this.numberOfSeats;
  }

  /**
   * Sets the car's number of seats.
   *
   * @param numberOfSeats the car's number of seats
>>>>>>> 38a1d958a529bbc8ffd5cea1670657fbc0bf645f
   */
  public void setNumberOfSeats(int numberOfSeats) {
    this.numberOfSeats = numberOfSeats;
  }

  /**
   * Returns the car's price per day.
   *
   * @return the car's price per day
   */
  public int getPricePerDay() {
    return this.pricePerDay;
  }

  /**
   * Sets the car's price per day.
   *
   * @param pricePerDay the car's price per day
   */
  public void setPricePerDay(int pricePerDay) {
    this.pricePerDay = pricePerDay;
  }

  /**
   * Returns the car's model.
   *
   * @return the car's model
   */
  public CarModel getModel() {
    return this.model;
  }

  /**
   * Returns the car's fuel type.
   *
   * @return the car's fuel type
   */
  public FuelType getFuelType() {
    return this.fuelType;
  }

  /**
   * Returns the car's transmission type.
   *
   * @return the car's transmission type
   */
  public TransmissionType getTransmissionType() {
    return this.transmissionType;
  }

  /**
   * Returns the car's addons.
   *
   * @return the car's addons
   */
  public List<Addon> getAddons() {
    return this.addons;
  }

  /**
   * Sets the car's addons.
   *
   * @param addons the car's addons
   */
  public void setAddons(List<Addon> addons) {
    this.addons = addons;
  }

  /**
   * Returns the car's features.
   *
   * @return the car's features
   */
  public List<Feature> getFeatures() {
    return this.features;
  }

  /**
   * Sets the car's features.
   *
   * @param features the car's features
   */
  public void setFeatures(List<Feature> features) {
    this.features = features;
  }

}
