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

  /**
   * Finds a set of cars whose model matches the specified model.
   *
   * @param model the car model to search for
   * @return a set of cars that match the specified model
   */
  Set<Car> findByModel(CarModel model);

  /**
   * Fins all the unique number of seats in all cars.
   *
   * @return a set of unique number of seats in all cars
   */
  @Query("""
      SELECT DISTINCT car.numberOfSeats
      FROM Car car
            ORDER BY car.numberOfSeats ASC
      """)
  Set<Integer> findAllAmountOfSeatsInCars();

    /**
     * Finds all cars that belong to a specific company.
     *
     * @param companyId the ID of the company
     * @return a list of cars that belong to the specified company
     */
  @Query("""
      SELECT company.cars
                  FROM Company company
                  WHERE company.id = :companyId
      """)
  List<Car> getCarsBelongingToCompany(Long companyId);

  List<Car> findAllByVisible(boolean visible);
}
