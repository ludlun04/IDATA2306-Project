package no.ntnu.stud.idata2306project.service;

import jakarta.transaction.Transactional;
import no.ntnu.stud.idata2306project.enums.ImageType;
import no.ntnu.stud.idata2306project.model.image.CarImage;
import no.ntnu.stud.idata2306project.repository.CarImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CarImageService {
  private final CarImageRepository carImageRepository;
  private static final Logger logger = LoggerFactory.getLogger(CarImageService.class);

  public CarImageService(CarImageRepository carImageRepository) {
    this.carImageRepository = carImageRepository;
    logger.info("CarImageService initialized");
  }

  /**
   * Get a car image by car ID, image type, and image width.
   *
   * @param carId the ID of the car
   * @param imageType the type of the image
   * @param imageWidth the width of the image
   * @return the car image
   */
  @Transactional
  public CarImage getCarImage(long carId, ImageType imageType, long imageWidth) {
    return carImageRepository.findCarImageByCarIdAndImageTypeAndImageWidth(carId, imageType, imageWidth);
  }
}
