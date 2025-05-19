package no.ntnu.stud.idata2306project.dto;

/**
 * Represents a request to mark a car as favorite or not favorite.
 *
 * <p>This class is used to transfer data between the frontend and backend
 * when a user wants to mark a car as favorite or not favorite.
 */
public class CarFavoriteRequestDto {
  private Long carId;
  private boolean isFavorite;

  /**
   * Creates a new instance of CarFavoriteRequestDto.
   *
   * @param carId the ID of the car
   * @param isFavorite true if the car is marked as favorite, false otherwise
   */
  public CarFavoriteRequestDto(Long carId, boolean isFavorite) {
    this.carId = carId;
    this.isFavorite = isFavorite;
  }

  /**
   * Sets the ID of the car.
   *
   * @return the ID of the car
   */
  public Long getCarId() {
    return carId;
  }

  /**
   * Sets the ID of the car.
   *
   * @param carId the ID of the car
   */
  public void setCarId(Long carId) {
    this.carId = carId;
  }

  /**
   * Checks if the car is marked as favorite.
   *
   * @return true if the car is marked as favorite, false otherwise
   */
  public boolean isFavorite() {
    return isFavorite;
  }

  /**
   * Sets the favorite status of the car.
   *
   * @param isFavorite true if the car is marked as favorite, false otherwise
   */
  public void setFavorite(boolean isFavorite) {
    this.isFavorite = isFavorite;
  }
}
