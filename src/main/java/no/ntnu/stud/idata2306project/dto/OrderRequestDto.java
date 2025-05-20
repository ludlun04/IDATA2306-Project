package no.ntnu.stud.idata2306project.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a request to create an order.
 */
public class OrderRequestDto { 
  private long carId;
  private List<Long> addonIds;
  private LocalDate endDate;
  private LocalDate startDate;

  /**
   * Gets the car ID.
   *
   * @return the car ID
   */
  public long getCarId() {
    return this.carId;
  }

  /**
   * Gets the end date of the order.
   *
   * @return the end date of the order
   */
  public LocalDate getEndDate() {
    return this.endDate;
  }

  /**
   * Gets the start date of the order.
   *
   * @return the start date of the order
   */
  public LocalDate getStartDate() {
    return this.startDate;
  }

  /**
   * Gets the list of addon IDs.
   *
   * @return the list of addon IDs
   */
  public List<Long> getAddonIds() {
    return this.addonIds;
  }

  /**
   * Sets the car ID.
   *
   * @param carId the car ID
   */
  public void setCarId(long carId) {
    this.carId = carId;
  }

  /**
   * Sets the start date of the order.
   *
   * @param startDate the start date of the order
   */
  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  /**
   * Sets the end date of the order.
   *
   * @param endDate the end date of the order
   */
  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  /**
   * Sets the list of addon IDs.
   *
   * @param addonIds the list of addon IDs
   */
  public void setAddonIds(List<Long> addonIds) {
    this.addonIds = addonIds;
  }
}
