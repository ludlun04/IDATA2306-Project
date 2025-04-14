package no.ntnu.stud.idata2306_project.model.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import no.ntnu.stud.idata2306_project.model.car.Addon;

/**
 * Represents an order
 */
@Entity
@Table(name = "rent_order")
public class Order {

  @Schema(description = "The id of the order")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long orderId;

  @Schema(description = "The id of the user who made the order")
  @NotNull
  private Long userId;

  @Schema(description = "The id of the car that was ordered")
  @NotNull
  private Long carId;

  @Schema(description = "The start date of the order")
  @NotNull
  private LocalDate startDate;

  @Schema(description = "The end date of the order")
  @NotNull
  private LocalDate endDate;

  @Schema(description = "The price the user paid for the order")
  private int price;

  @Schema(description = "The addons that were included in the order")
  @ManyToMany(fetch = FetchType.EAGER)
  private List<Addon> addons;

  /**
   * Creates a new order
   */
  public Order() {}

  /**
   * Creates a new order
   *
   * @param userId the id of the user who made the order
   * @param carId the id of the car that was ordered
   * @param startDate the start date of the order
   * @param endDate the end date of the order
   * @param price the price the user paid for the order
   * @param addons the addons that were included in the order
   */
  public Order(Long userId, Long carId, LocalDate startDate, LocalDate endDate, int price, List<Addon> addons) {
    this.userId = userId;
    this.carId = carId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.price = price;
    this.addons = addons;
  }

  /**
   * Returns the id of the order
   * @return the id of the order
   */
  public long getOrderId() {
    return orderId;
  }

  /**
   * Returns the id of the user who made the order
   * @return the id of the user who made the order
   */
  public Long getUserId() {
      return userId;
  }

  /**
   * Returns the id of the car that was ordered
   * @return the id of the car that was ordered
   */
  public Long getCarId() {
      return carId;
  }

  /**
   * Returns the start date of the order
   * @return the start date of the order
   */
  public LocalDate getStartDate() {
      return startDate;
  }

  /**
   * Returns the end date of the order
   * @return the end date of the order
   */
  public LocalDate getEndDate() {
      return endDate;
  }

  /**
   * Returns the price the user paid for the order
   * @return the price the user paid for the order
   */
  public int getPrice() {
      return price;
  }

  /**
   * Returns the addons that were included in the order
   * @return the addons that were included in the order
   */
  public List<Addon> getAddons() {
      return addons;
  }
}
