package no.ntnu.stud.idata2306project.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import no.ntnu.stud.idata2306project.model.car.Addon;
import no.ntnu.stud.idata2306project.model.car.Car;
import no.ntnu.stud.idata2306project.model.order.Order;
import no.ntnu.stud.idata2306project.model.user.User;

public class OrderResponseDto {
  private long orderId;

  private User user;

  private CarDto car;

  private LocalDate startDate;

  private LocalDate endDate;

  private long price;

  private List<Addon> addons;

  public OrderResponseDto(Order order, CarDto car) {
    this.orderId = order.getOrderId();
    this.user = order.getUser();
    this.car = car;
    this.startDate = order.getStartDate();
    this.endDate = order.getEndDate();
    this.price = order.getPrice();
    this.addons = new ArrayList<>(order.getAddons());
  }

  public long getOrderId() {
      return orderId;
  }

  public void setOrderId(long orderId) {
      this.orderId = orderId;
  }

  public User getUser() {
      return user;
  }

  public void setUser(User user) {
      this.user = user;
  }

  public CarDto getCar() {
      return car;
  }

  public void setCar(CarDto car) {
      this.car = car;
  }

  public LocalDate getStartDate() {
      return startDate;
  }

  public void setStartDate(LocalDate startDate) {
      this.startDate = startDate;
  }

  public LocalDate getEndDate() {
      return endDate;
  }

  public void setEndDate(LocalDate endDate) {
      this.endDate = endDate;
  }

  public long getPrice() {
      return price;
  }

  public void setPrice(long price) {
      this.price = price;
  }

  public List<Addon> getAddons() {
      return addons;
  }

  public void setAddons(List<Addon> addons) {
      this.addons = addons;
  }
}
