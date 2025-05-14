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
 * This class is used to transfer car data between the frontend and backend.
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

  public void setId(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getYear() {
    return year;
  }

  public void setNumberOfSeats(int numberOfSeats) {
    this.numberOfSeats = numberOfSeats;
  }

  public int getNumberOfSeats() {
    return numberOfSeats;
  }

  public void setPricePerDay(int pricePerDay) {
    this.pricePerDay = pricePerDay;
  }

  public int getPricePerDay() {
    return pricePerDay;
  }

  public void setFuelType(FuelType fuelType) {
    this.fuelType = fuelType;
  }

  public FuelType getFuelType() {
    return fuelType;
  }

  public void setTransmissionType(TransmissionType transmissionType) {
    this.transmissionType = transmissionType;
  }

  public TransmissionType getTransmissionType() {
    return transmissionType;
  }

  public void setModel(CarModel model) {
    this.model = model;
  }

  public CarModel getModel() {
    return this.model;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  public Company getCompany() {
    return company;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void setAddons(List<Addon> addons) {
    this.addons = addons;
  }

  public List<Addon> getAddons() {
    return addons;
  }

  public void setFeatures(List<Feature> features) {
    this.features = features;
  }

  public List<Feature> getFeatures() {
    return features;
  }
}
