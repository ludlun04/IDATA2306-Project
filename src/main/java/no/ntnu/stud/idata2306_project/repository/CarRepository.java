package no.ntnu.stud.idata2306_project.repository;

import no.ntnu.stud.idata2306_project.model.car.CarBrand;
import no.ntnu.stud.idata2306_project.model.car.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.stud.idata2306_project.model.car.Car;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
  List<Car> findByModel(CarModel model);
}
