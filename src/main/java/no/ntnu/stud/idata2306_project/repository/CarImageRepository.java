package no.ntnu.stud.idata2306_project.repository;

import no.ntnu.stud.idata2306_project.enums.ImageType;
import no.ntnu.stud.idata2306_project.model.image.CarImage;
import org.springframework.data.repository.ListCrudRepository;

public interface CarImageRepository  extends ListCrudRepository<CarImage, Long> {
  CarImage findCarImageByCarIdAndImageTypeAndImageWidth(long carId, ImageType imageType, long imageWidth);
}
