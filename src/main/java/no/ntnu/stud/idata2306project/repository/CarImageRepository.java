package no.ntnu.stud.idata2306project.repository;

import no.ntnu.stud.idata2306project.enums.ImageType;
import no.ntnu.stud.idata2306project.model.image.CarImage;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Repository interface for managing {@link CarImage} entities.
 *
 * <p>This interface extends the {@link ListCrudRepository} to
 * provide CRUD operations for {@link CarImage} entities.
 * It also includes custom query methods to retrieve car images based on
 * specific criteria.
 */
public interface CarImageRepository extends ListCrudRepository<CarImage, Long> {

  /**
   * Finds a car image by its car ID, image type, and image width.
   *
   * @param carId the ID of the car
   * @param imageType the type of the image
   * @param imageWidth the width of the image
   * @return the car image that matches the specified criteria
   */
  CarImage findCarImageByCarIdAndImageTypeAndImageWidth(
      long carId,
      ImageType imageType,
      long imageWidth);
}
