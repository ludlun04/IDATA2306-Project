package no.ntnu.stud.idata2306project.repository;

import no.ntnu.stud.idata2306project.enums.ImageType;
import no.ntnu.stud.idata2306project.model.image.CarImage;
import org.springframework.data.repository.ListCrudRepository;

public interface CarImageRepository  extends ListCrudRepository<CarImage, Long> {
  CarImage findCarImageByCarIdAndImageTypeAndImageWidth(long carId, ImageType imageType, long imageWidth);
}
