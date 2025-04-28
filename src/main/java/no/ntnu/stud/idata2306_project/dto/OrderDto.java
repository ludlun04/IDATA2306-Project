package no.ntnu.stud.idata2306_project.dto;

import java.time.LocalDate;

public class OrderDto {

  private long orderId;
  private long userId;
  private long price;
  private long carId;
  private LocalDate endDate;
  private LocalDate startDate;

  public long getCarId() {
    return this.carId;
  }

  public long getOrderId() {
    return orderId;
  }

  public long getPrice() {
    return price;
  }

  public long getUserId() {
    return userId;
  }

  public LocalDate getEndDate() {
    return this.endDate;
  }

  public LocalDate getStartDate() {
    return this.startDate;
  }

  public void setCarId(long carId) {
    this.carId = carId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public void setPrice(long price) {
    this.price = price;
  }
}
