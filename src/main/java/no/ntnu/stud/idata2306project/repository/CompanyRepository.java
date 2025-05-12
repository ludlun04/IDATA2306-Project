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
      SELECT DISTINCT company
            FROM Company company JOIN Car car ON company.id = car.company.id
      """)
  Set<Company> findAllInUseOnCars();

  Set<Company> findAllByUsers_Id(long usersId);

  @Query("""
      SELECT DISTINCT car
                  FROM Company company JOIN Car car ON company.id = car.company.id
                  WHERE company.id = :companyId
      """)
  List<Car> getCarsBelongingToCompany(Long companyId);

  @Query("""
      SELECT DISTINCT company
                  FROM Company company JOIN Car car ON company.id = car.company.id
                  WHERE car.id = :carId
      """)
  Company findCompanyThatOwnsCar(Long carId);
}
