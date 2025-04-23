package no.ntnu.stud.idata2306_project.controller;

import no.ntnu.stud.idata2306_project.model.car.FuelType;
import no.ntnu.stud.idata2306_project.service.FuelTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fuel")
public class FuelTypeController {
  private final FuelTypeService fuelTypeService;
  private final Logger logger = LoggerFactory.getLogger(FuelTypeController.class);

  public FuelTypeController(FuelTypeService fuelTypeService) {
    this.fuelTypeService = fuelTypeService;
  }

  @GetMapping("/with_rentals")
  public ResponseEntity<List<FuelType>> getFuelTypesUsedInCars() {
    this.logger.info("Getting fuel types used in cars");
    return ResponseEntity.ok(fuelTypeService.getFuelTypesUsedInCars().stream().toList());
  }
}
