package no.ntnu.stud.idata2306project.model.image;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import no.ntnu.stud.idata2306project.enums.ImageType;

/**
 * Represents a car image.
 */
@Entity
public class CarImage {

  @Schema(description = "The id of the image", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Schema(description = "The image of the car")
  @NotNull
  @Lob
  private byte[] image;

  @Schema(description = "The id of the car", example = "1")
  @NotNull
  private long carId;

  @Schema(description = "The width of the image", example = "1920")
  @NotNull
  private long imageWidth;

  @Schema(description = "The type of the image", example = "JPEG")
  private ImageType imageType;

  /**
   * Creates a new car image.
   */
  public CarImage() {
  }

  /**
   * Creates a new car image.
   *
   * @param carId the id of the car
   * @param image the image of the car
   * @param imageWidth the width of the image
   * @param imageType the type of the image
   */
  public CarImage(long carId, byte[] image, long imageWidth, ImageType imageType) {
    this.carId = carId;
    this.image = image;
    this.imageWidth = imageWidth;
    this.imageType = imageType;
  }

  /**
   * Returns the id of the car.
   */
  public long getCarId() {
    return carId;
  }

  /**
   * Returns the image of the car.
   */
  public byte[] getImage() {
    return image;
  }

  /**
   * Returns the width of the image.
   */
  public long getImageWidth() {
    return imageWidth;
  }

  /**
   * Returns the type of the image.
   */
  public ImageType getImageType() {
    return imageType;
  }
}
