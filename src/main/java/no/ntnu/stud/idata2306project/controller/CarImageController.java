package no.ntnu.stud.idata2306project.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Base64;
import no.ntnu.stud.idata2306project.enums.ImageType;
import no.ntnu.stud.idata2306project.model.image.CarImage;
import no.ntnu.stud.idata2306project.service.CarImageService;
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
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CarImageController.class);

  public CarImageController(CarImageService carImageService) {
    this.carImageService = carImageService;
  }

  @GetMapping("/{carId}/{imageType}/{imageWidth}")
  public ResponseEntity<String> getCarImage(@PathVariable long carId,
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

    CarImage carImage = carImageService.getCarImage(carId, type, imageWidth);
    if (carImage == null || carImage.getImage() == null) {
      return ResponseEntity.ok().header("Content-Type", "text/plain").body("");
    }

    String base64Image = Base64.getEncoder().encodeToString(carImage.getImage());

    logger.info("Returning image of type {} for car with id {}", type, carId);

    return ResponseEntity.ok()
        .header("Content-Type", "text/plain")
        .body(base64Image);
  }
}
