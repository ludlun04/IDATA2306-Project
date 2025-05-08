package no.ntnu.stud.idata2306_project.repository;

import no.ntnu.stud.idata2306_project.model.car.Car;
import no.ntnu.stud.idata2306_project.model.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import no.ntnu.stud.idata2306_project.model.company.Company;

import java.util.List;
import java.util.Set;

public interface CompanyRepository extends ListCrudRepository<Company, Long> {

  @Query(
    """
      SELECT DISTINCT company
            FROM Company company JOIN Car car ON company.id = car.company.id
      """)
  Set<Company> findAllInUseOnCars();

  Set<Company> findAllByUsers_Id(long usersId);

  @Query(
    """
      SELECT DISTINCT car
                  FROM Company company JOIN Car car ON company.id = car.company.id
                  WHERE company.id = :companyId
      """
  )
  List<Car> getCarsBelongingToCompany(Long companyId);
}
