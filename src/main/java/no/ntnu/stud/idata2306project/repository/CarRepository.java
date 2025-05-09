package no.ntnu.stud.idata2306project.repository;

import no.ntnu.stud.idata2306project.model.car.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

import no.ntnu.stud.idata2306project.model.car.Car;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface CarRepository extends JpaRepository<Car, Long> {
  Set<Car> findByModel(CarModel model);

  @Query(
    """
      SELECT DISTINCT car.numberOfSeats
      FROM Car car
            ORDER BY car.numberOfSeats ASC
      """
  )
  Set<Integer> findAllAmountOfSeatsInCars();

  List<Car> findAllByVisible(boolean visible);
}
