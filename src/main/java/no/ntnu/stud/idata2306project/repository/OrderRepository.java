package no.ntnu.stud.idata2306project.repository;

import java.time.LocalDate;
import java.util.List;
import no.ntnu.stud.idata2306project.model.order.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Repository interface for managing {@link Order} entities.
 *
 * <p>This interface extends the {@link ListCrudRepository} to
 * provide CRUD operations for {@link Order} entities.
 * It also includes custom query methods to retrieve orders based on
 * specific criteria.
 */
public interface OrderRepository extends ListCrudRepository<Order, Long> {

  /**
   * Finds a list of orders associated with a specific user ID.
   *
   * @param id the ID of the user
   * @return a list of orders associated with the specified user ID
   */
  @Query("""
      SELECT o
      FROM Order o
      WHERE o.user.id = :id
      """)
  public List<Order> findOrdersByUserId(Long id);

  /**
   * Finds a single order associated with a specific user ID.
   *
   * @param id the ID of the user
   * @return an order associated with the specified user ID
   */
  @Query("""
      SELECT o
      FROM Order o
      WHERE o.user.id = :id
      """)
  public Order findOrderByUserId(Long id);

  /**
   * Checks if a car is available for a specific date.
   *
   * @param carId the ID of the car
   * @param startDate the start date to check availability
   * @return true if the car is available, false otherwise
   */
  @Query("""
      SELECT CASE WHEN COUNT(o) = 0 THEN true ELSE false END
      FROM Order o
      WHERE o.car.id = :carId
      AND (o.startDate <= :startDate AND o.endDate > :startDate)
      """)
  boolean isAvailableFrom(Long carId, LocalDate startDate);

  /**
   * Checks if a car is available for a specific date range.
   *
   * @param carId the ID of the car
   * @param startDate the start date to check availability
   * @param endDate the end date to check availability
   * @return true if the car is available, false otherwise
   */
  @Query("""
        SELECT CASE WHEN COUNT(o) = 0 THEN true ELSE false END
        FROM Order o
        WHERE o.car.id = :carId
            AND o.startDate < :endDate
            AND o.endDate > :startDate
      """)
  boolean isAvailableBetween(Long carId, LocalDate startDate, LocalDate endDate);

  /**
   * Finds a list of active orders associated with a specific user ID.
   *
   * @param userId the ID of the user
   * @return a list of active orders associated with the specified user ID
   */
  @Query("""
      SELECT o
      FROM Order o
      WHERE o.user.id = :userId
          AND (CURRENT_DATE between o.startDate AND o.endDate)
      """)
  List<Order> findActiveOrdersByUserId(Long userId);

  /**
   * Finds a list of orders associated with a specific car ID.
   *
   * @param carId the ID of the car
   * @return a list of orders associated with the specified car ID
   */
  List<Order> findAllByCar_Id(long carId);

  /**
   * Finds a list of orders associated with a specific company ID.
   *
   * @param companyId the ID of the company
   * @return a list of orders associated with the specified company ID
   */
  @Query("""
      SELECT o
          FROM Order o JOIN Car car ON o.car.id = car.id JOIN Company c ON car IN (
                SELECT c.cars
                      FROM Company c
                      WHERE c.id = :companyId
                )
      """)
  List<Order> findAllOrdersByCompany_Id(Long companyId);
}
