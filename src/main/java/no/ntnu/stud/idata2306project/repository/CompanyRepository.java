package no.ntnu.stud.idata2306project.repository;

import java.util.List;
import java.util.Set;
import no.ntnu.stud.idata2306project.model.car.Car;
import no.ntnu.stud.idata2306project.model.company.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Repository interface for managing {@link Company} entities.
 *
 * <p>This interface extends the {@link ListCrudRepository} to 
 * provide CRUD operations for {@link Company}
 * entities. It also includes custom query methods to retrieve companies based on specific criteria.
 */
public interface CompanyRepository extends ListCrudRepository<Company, Long> {

  /**
   * Finds all companies that have cars.
   *
   * @return a set of companies that have cars
   */
  @Query("""
      SELECT company
            FROM Company company
                  WHERE company.cars IS NOT EMPTY
      """)
  Set<Company> findAllInUseOnCars();

  /**
   * Find all companies a user belongs to.
   *
   * @param usersId the ID of the user
   * @return a set of companies that the user belongs to
   */
  Set<Company> findAllByUsers_Id(long usersId);

  /**
   * Finds all companies that have a specific car.
   *
   * @param car the car to search for
   * @return a list of companies that have the specified car
   */
  @Query("""
      SELECT company
                  FROM Company company
                  WHERE :carId IN (
                        SELECT car.id
                              FROM company.cars car
                              WHERE car.id = :carId
                        )
      """)
  Company findCompanyThatOwnsCar(Long carId);
}
