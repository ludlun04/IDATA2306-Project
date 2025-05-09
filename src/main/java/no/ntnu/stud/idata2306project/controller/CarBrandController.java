package no.ntnu.stud.idata2306project.controller;

import no.ntnu.stud.idata2306project.model.car.CarBrand;
import no.ntnu.stud.idata2306project.service.CarBrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class CarBrandController {

  private CarBrandService brandService;
  private Logger logger = LoggerFactory.getLogger(CarBrandController.class);

  public CarBrandController(CarBrandService brandService) {
    this.brandService = brandService;
  }

  @GetMapping("/with_rentals")
  public ResponseEntity<List<CarBrand>> getCarBrandsUsedInCars() {
    this.logger.info("Getting car brands used in cars");
    return ResponseEntity.ok(brandService.getCarBrandsUsedInCars().stream().toList());
  }


}
