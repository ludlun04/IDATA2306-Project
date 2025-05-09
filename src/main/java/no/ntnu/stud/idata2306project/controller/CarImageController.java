package no.ntnu.stud.idata2306project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Base64;
import no.ntnu.stud.idata2306project.enums.ImageType;
import no.ntnu.stud.idata2306project.model.image.CarImage;
import no.ntnu.stud.idata2306project.service.CarImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling car images.
 *
 * <p>Has the following endpoints:
 * <ul>
 *   <li> Get car image by car id, image type and image width
 * </ul>
 */
@Tag(name = "Car Image", description = "Controller for car images")
@RestController
@RequestMapping("/image")
public class CarImageController {
  private final CarImageService carImageService;
  private static final Logger logger = LoggerFactory.getLogger(CarImageController.class);

  /**
   * Create a new CarImageController.
   *
   * @param carImageService the car image service
   */
  public CarImageController(CarImageService carImageService) {
    this.carImageService = carImageService;
  }

  /**
   * Get car image by car id, image type and image width.
   *
   * @param carId the car id
   * @param imageType the image type
   * @param imageWidth the image width
   * @return the car image
   */
  @Operation(
      summary = "Get car image",
      description = "Get car image by car id, image type and image width"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Car image found"),
      @ApiResponse(responseCode = "400", description = "Invalid car id, image type or image width"),
  })
  @GetMapping("/{carId}/{imageType}/{imageWidth}")
  public ResponseEntity<String> getCarImage(
      @Parameter(name = "carId", description = "Car ID", required = true)
      @PathVariable long carId,
      @Parameter(name = "imageType", description = "Image type", required = true)
      @PathVariable String imageType,
      @Parameter(name = "imageWidth", description = "Image width", required = true)
      @PathVariable long imageWidth
  ) {
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
