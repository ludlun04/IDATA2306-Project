package no.ntnu.stud.idata2306project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import no.ntnu.stud.idata2306project.model.car.CarBrand;
import no.ntnu.stud.idata2306project.service.CarBrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represents a controller for car brands.
 *
 * <p>Contains the following endpoints:
 * <ul>
 *   <li> Get car brands used in cars
 * </ul>
 */
@Tag(name = "CarBrand", description = "Endpoints for car brands")
@RestController
@RequestMapping("/brand")
public class CarBrandController {

  private final CarBrandService brandService;
  private final Logger logger = LoggerFactory.getLogger(CarBrandController.class);

  /**
   * Creates a new CarBrandController.
   *
   * @param brandService the car brand service to use
   */
  public CarBrandController(CarBrandService brandService) {
    this.brandService = brandService;
  }

  /**
   * Gets all car brands used in cars.
   *
   * @return a list of car brands used in cars
   */
  @Operation(summary = "Get car brands used in cars",
      description = "Get all car brands used in cars")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Car brands used in cars"),
  })
  @GetMapping("/with_rentals")
  public ResponseEntity<List<CarBrand>> getCarBrandsUsedInCars() {
    this.logger.info("Getting car brands used in cars");
    return ResponseEntity.ok(brandService.getCarBrandsUsedInCars().stream().toList());
  }


}
