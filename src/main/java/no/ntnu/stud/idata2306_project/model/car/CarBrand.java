package no.ntnu.stud.idata2306_project.model.car;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class CarBrand {

  @Schema(description = "The id of the car brand", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "brand_id", nullable = false, updatable = false)
  long id;

  @Schema(description = "The name of the car brand", example = "Toyota")
  @NotNull
  @NotEmpty
  @NotBlank
  String name;

  public CarBrand() {}

  public CarBrand(long id, String name) {
      this.id = id;
      this.name = name;
  }

  public long getId() {
      return this.id;
  }

  public String getName() {
      return this.name;
  }
}
