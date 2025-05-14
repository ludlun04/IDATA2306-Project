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

  @Query("""
      SELECT company
            FROM Company company
                  WHERE company.cars IS NOT EMPTY
      """)
  Set<Company> findAllInUseOnCars();

  Set<Company> findAllByUsers_Id(long usersId);

  @Query("""
      SELECT company.cars
                  FROM Company company
                  WHERE company.id = :companyId
      """)
  List<Car> getCarsBelongingToCompany(Long companyId);

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
