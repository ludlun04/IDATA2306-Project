package no.ntnu.stud.idata2306project.dto;

import java.util.List;
import no.ntnu.stud.idata2306project.model.car.Addon;
import no.ntnu.stud.idata2306project.model.car.CarModel;
import no.ntnu.stud.idata2306project.model.car.Feature;
import no.ntnu.stud.idata2306project.model.car.FuelType;
import no.ntnu.stud.idata2306project.model.car.TransmissionType;
import no.ntnu.stud.idata2306project.model.company.Company;

/**
 * Represents a car data transfer object (DTO).
 *
 * <p>This class is used to transfer car data between the frontend and backend.
 */
public class CarDto {
  private long id;
  private int year;
  private int numberOfSeats;
  private int pricePerDay;
  private FuelType fuelType;
  private TransmissionType transmissionType;
  private CarModel model;
  private Company company;
  private String description;
  private List<Addon> addons;
  private List<Feature> features;
  private boolean visible;

  /**
   * Sets the id of the car dto.
   *
   * @param id the id of the car dto
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Gets the id of the car dto.
   *
   * @return the id of the car dto
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the year of the car dto.
   *
   * @param year the year of the car dto
   */
  public void setYear(int year) {
    this.year = year;
  }

  /**
   * Gets the year of the car dto.
   *
   * @return the year of the car dto
   */
  public int getYear() {
    return year;
  }

  /**
   * Sets the number of seats of the car dto.
   *
   * @param numberOfSeats the number of seats of the car dto
   */
  public void setNumberOfSeats(int numberOfSeats) {
    this.numberOfSeats = numberOfSeats;
  }

  /**
   * Gets the number of seats of the car dto.
   *
   * @return the number of seats of the car dto
   */
  public int getNumberOfSeats() {
    return numberOfSeats;
  }

  /**
   * Sets the price per day of the car dto.
   *
   * @param pricePerDay the price per day of the car dto
   */
  public void setPricePerDay(int pricePerDay) {
    this.pricePerDay = pricePerDay;
  }

  /**
   * Gets the price per day of the car dto.
   *
   * @return the price per day of the car dto
   */
  public int getPricePerDay() {
    return pricePerDay;
  }

  /**
   * Sets the fuel type of the car dto.
   *
   * @param fuelType the fuel type of the car dto
   */
  public void setFuelType(FuelType fuelType) {
    this.fuelType = fuelType;
  }

  /**
   * Gets the fuel type of the car dto.
   *
   * @return the fuel type of the car dto
   */
  public FuelType getFuelType() {
    return fuelType;
  }

  /**
   * Sets the transmission type of the car dto.
   *
   * @param transmissionType the transmission type of the car dto
   */
  public void setTransmissionType(TransmissionType transmissionType) {
    this.transmissionType = transmissionType;
  }

  /**
   * Gets the transmission type of the car dto.
   *
   * @return the transmission type of the car dto
   */
  public TransmissionType getTransmissionType() {
    return transmissionType;
  }

  /**
   * Sets the model of the car dto.
   *
   * @param model the model of the car dto
   */
  public void setModel(CarModel model) {
    this.model = model;
  }

  /**
   * Gets the model of the car dto.
   *
   * @return the model of the car dto
   */
  public CarModel getModel() {
    return this.model;
  }

  /**
   * Sets the company of the car dto.
   *
   * @param company the company of the car dto
   */
  public void setCompany(Company company) {
    this.company = company;
  }

  /**
   * Gets the company of the car dto.
   *
   * @return the company of the car dto
   */
  public Company getCompany() {
    return company;
  }

  /**
   * Sets the description of the car dto.
   *
   * @param description the description of the car dto
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the description of the car dto.
   *
   * @return the description of the car dto
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the addons of the car dto.
   *
   * @param addons the addons of the car dto
   */
  public void setAddons(List<Addon> addons) {
    this.addons = addons;
  }

  /**
   * Gets the addons of the car dto.
   *
   * @return the addons of the car dto
   */
  public List<Addon> getAddons() {
    return addons;
  }

  /**
   * Sets the features of the car dto.
   *
   * @param features the features of the car dto
   */
  public void setFeatures(List<Feature> features) {
    this.features = features;
  }

  /**
   * Gets the features of the car dto.
   *
   * @return the features of the car dto
   */
  public List<Feature> getFeatures() {
    return features;
  }

  /**
   * Sets the visibility of the car dto.
   *
   * @param visible the visibility of the car dto
   */
  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  /**
   * Gets the visibility of the car dto.
   *
   * @return the visibility of the car dto
   */
  public boolean getVisible() {
    return visible;
  }
}
