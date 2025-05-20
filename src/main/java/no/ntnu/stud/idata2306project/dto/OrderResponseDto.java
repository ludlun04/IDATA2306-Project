package no.ntnu.stud.idata2306project.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.stud.idata2306project.model.car.Addon;
import no.ntnu.stud.idata2306project.model.order.Order;
import no.ntnu.stud.idata2306project.model.user.User;

/**
 * Represents a response containing order details.
 */
public class OrderResponseDto {
  private long orderId;

  private User user;

  private CarDto car;

  private LocalDate startDate;

  private LocalDate endDate;

  private long price;

  private List<Addon> addons;

  /**
   * Creates a new order response.
   *
   * @param order the order
   * @param car   the car
   */
  public OrderResponseDto(Order order, CarDto car) {
    this.orderId = order.getOrderId();
    this.user = order.getUser();
    this.car = car;
    this.startDate = order.getStartDate();
    this.endDate = order.getEndDate();
    this.price = order.getPrice();
    this.addons = new ArrayList<>(order.getAddons());
  }

  /**
   * Gets the order ID.
   *
   * @return the order ID
   */
  public long getOrderId() {
    return orderId;
  }

  /**
   * Sets the order ID.
   *
   * @param orderId the order ID
   */
  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  /**
   * Gets the user who made the order.
   *
   * @return the user
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets the user who made the order.
   *
   * @param user the user
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Gets the car that was ordered.
   *
   * @return the car
   */
  public CarDto getCar() {
    return car;
  }

  /**
   * Sets the car that was ordered.
   *
   * @param car the car
   */
  public void setCar(CarDto car) {
    this.car = car;
  }

  /**
   * Gets the start date of the order.
   *
   * @return the start date
   */
  public LocalDate getStartDate() {
    return startDate;
  }

  /**
   * Sets the start date of the order.
   *
   * @param startDate the start date
   */
  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  /**
   * Gets the end date of the order.
   *
   * @return the end date
   */
  public LocalDate getEndDate() {
    return endDate;
  }

  /**
   * Sets the end date of the order.
   *
   * @param endDate the end date
   */
  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  /**
   * Gets the price of the order.
   *
   * @return the price
   */
  public long getPrice() {
    return price;
  }

  /**
   * Sets the price of the order.
   *
   * @param price the price
   */
  public void setPrice(long price) {
    this.price = price;
  }

  /**
   * Gets the addons included in the order.
   *
   * @return the addons
   */
  public List<Addon> getAddons() {
    return addons;
  }

  /**
   * Sets the addons included in the order.
   *
   * @param addons the addons
   */
  public void setAddons(List<Addon> addons) {
    this.addons = addons;
  }
}
