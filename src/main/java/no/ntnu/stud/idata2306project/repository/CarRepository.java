package no.ntnu.stud.idata2306project.repository;

import java.util.List;
import java.util.Set;
import no.ntnu.stud.idata2306project.model.car.Car;
import no.ntnu.stud.idata2306project.model.car.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository interface for managing {@link Car} entities.
 *
 * <p>This interface extends the {@link JpaRepository} to provide CRUD operations
 * for {@link Car}
 * entities. It also includes custom query methods to retrieve cars based on
 * specific criteria.
 */
public interface CarRepository extends JpaRepository<Car, Long> {
  Set<Car> findByModel(CarModel model);

  @Query("""
      SELECT DISTINCT car.numberOfSeats
      FROM Car car
            ORDER BY car.numberOfSeats ASC
      """)
  Set<Integer> findAllAmountOfSeatsInCars();

  List<Car> findAllByVisible(boolean visible);
}
