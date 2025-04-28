package no.ntnu.stud.idata2306_project.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.sql.Blob;
import no.ntnu.stud.idata2306_project.enums.ImageType;
import no.ntnu.stud.idata2306_project.model.image.CarImage;
import no.ntnu.stud.idata2306_project.service.CarImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CarImage", description = "Controller for car images")
@RestController
@RequestMapping("/image")
public class CarImageController {
  private CarImageService carImageService;

  public CarImageController(CarImageService carImageService) {
    this.carImageService = carImageService;
  }

  @GetMapping("/{carId}/{imageType}/{imageWidth}")
  public ResponseEntity<CarImage> getCarImage(@PathVariable long carId,
                                              @PathVariable String imageType,
                                              @PathVariable long imageWidth) {
    ImageType type;
    if (carId <= 0 || imageWidth <= 0) {
      return ResponseEntity.badRequest().build();
    }
    switch (imageType) {
      case "webp":
        type = ImageType.WEBP;
        break;
      case "png":
        type = ImageType.PNG;
        break;
      case "jpg":
        type = ImageType.JPG;
        break;
      default:
        return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(carImageService.getCarImage(carId, type, imageWidth));
  }
}
