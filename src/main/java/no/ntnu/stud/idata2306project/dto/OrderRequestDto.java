package no.ntnu.stud.idata2306project.dto;

import java.time.LocalDate;
import java.util.List;

public class OrderRequestDto { 
  private long carId;
  private List<Long> addonIds;
  private LocalDate endDate;
  private LocalDate startDate;

  public long getCarId() {
    return this.carId;
  }

  public LocalDate getEndDate() {
    return this.endDate;
  }

  public LocalDate getStartDate() {
    return this.startDate;
  }

  public List<Long> getAddonIds() {
    return this.addonIds;
  }

  public void setCarId(long carId) {
    this.carId = carId;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public void setAddonIds(List<Long> addonIds) {
    this.addonIds = addonIds;
  }
}
