package no.ntnu.stud.idata2306_project.model.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import no.ntnu.stud.idata2306_project.model.car.Addon;

@Entity
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int orderId;

  @NotBlank
  private int userId;

  @NotBlank
  private int carId;

  @NotBlank
  private Date startDate;

  @NotBlank
  private Date endDate;

  @NotBlank
  private int price;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Addon> addons;

  public Order() {}

  public int getOrderId() {
    return orderId;
  }

  public int getUserId() {
      return userId;
  }

  public int getCarId() {
      return carId;
  }

  public Date getStartDate() {
      return startDate;
  }

  public Date getEndDate() {
      return endDate;
  }

  public int getPrice() {
      return price;
  }

  public List<Addon> getAddons() {
      return addons;
  }
}
