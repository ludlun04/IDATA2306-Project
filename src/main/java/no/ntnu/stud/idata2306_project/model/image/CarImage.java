package no.ntnu.stud.idata2306_project.model.image;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import java.sql.Blob;
import no.ntnu.stud.idata2306_project.enums.ImageType;

@Entity
public class CarImage {

  @Schema(description = "The id of the image", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Schema(description = "The image of the car")
  @NotNull
  @Lob
  private Blob image;

  @Schema(description = "The id of the car", example = "1")
  @NotNull
  private long carId;

  @Schema(description = "The width of the image", example = "1920")
  @NotNull
  private long imageWidth;

  @Schema(description = "The type of the image", example = "JPEG")
  @NotNull
  private ImageType imageType;

  public CarImage() {
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
  public Blob getImage() {
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
