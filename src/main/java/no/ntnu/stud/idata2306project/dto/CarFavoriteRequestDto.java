package no.ntnu.stud.idata2306project.dto;

public class CarFavoriteRequestDto {
  private Long carId;
  private boolean isFavorite;

  public CarFavoriteRequestDto(Long carId, boolean isFavorite) {
    this.carId = carId;
    this.isFavorite = isFavorite;
  }

  public Long getCarId() {
    return carId;
  }

  public void setCarId(Long carId) {
    this.carId = carId;
  }

  public boolean isFavorite() {
    return isFavorite;
  }

  public void setFavorite(boolean isFavorite) {
    this.isFavorite = isFavorite;
  }
}
