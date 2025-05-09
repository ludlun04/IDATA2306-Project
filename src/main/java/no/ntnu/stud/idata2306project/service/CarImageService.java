package no.ntnu.stud.idata2306project.service;

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
  public CarImage getCarImage(long carId, ImageType imageType, long imageWidth) {
    return carImageRepository.findCarImageByCarIdAndImageTypeAndImageWidth(carId, imageType, imageWidth);
  }
}
