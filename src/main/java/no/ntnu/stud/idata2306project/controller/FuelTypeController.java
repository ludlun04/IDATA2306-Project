package no.ntnu.stud.idata2306project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import no.ntnu.stud.idata2306project.model.car.FuelType;
import no.ntnu.stud.idata2306project.service.FuelTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represents a controller for fuel types.
 *
 * <p>Contains the following endpoints:
 * <ul>
 *   <li> Get all fuel types used in cars
 * </ul>
 */
@Tag(name = "Fuel Types", description = "Endpoints for managing fuel types")
@RestController
@RequestMapping("/fuel")
public class FuelTypeController {
  private final FuelTypeService fuelTypeService;
  private final Logger logger = LoggerFactory.getLogger(FuelTypeController.class);

  /**
   * Creates a new fuel type controller.
   *
   * @param fuelTypeService the fuel type service
   */
  public FuelTypeController(FuelTypeService fuelTypeService) {
    this.fuelTypeService = fuelTypeService;
  }

  /**
   * Gets all fuel types used in cars.
   *
   * @return a list of fuel types used in cars
   */
  @Operation(
      summary = "Get all fuel types used in cars",
      description = "Gets all fuel types used in cars"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved fuel types"),
  })
  @GetMapping("/with_rentals")
  public ResponseEntity<List<FuelType>> getFuelTypesUsedInCars() {
    this.logger.info("Getting fuel types used in cars");
    return ResponseEntity.ok(fuelTypeService.getFuelTypesUsedInCars().stream().toList());
  }
}
