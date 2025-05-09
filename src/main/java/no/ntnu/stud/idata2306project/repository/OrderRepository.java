package no.ntnu.stud.idata2306project.repository;

import java.time.LocalDate;
import java.util.List;

import no.ntnu.stud.idata2306project.model.order.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface OrderRepository extends ListCrudRepository<Order, Long> {
  @Query("""
    SELECT o
    FROM Order o
    WHERE o.user.id = :id
    """)
  public List<Order> findOrdersByUserId(Long id);


  @Query("""
    SELECT o
    FROM Order o
    WHERE o.user.id = :id
    """)
  public Order findOrderByUserId(Long id);

  @Query("""
    SELECT CASE WHEN COUNT(o) = 0 THEN true ELSE false END
    FROM Order o
    WHERE o.car.id = :carId
    AND (o.startDate <= :startDate AND o.endDate > :startDate)
    """)
  boolean isAvailableFrom(Long carId, LocalDate startDate);

  @Query("""
    SELECT CASE WHEN COUNT(o) = 0 THEN true ELSE false END
    FROM Order o
    WHERE o.car.id = :carId
        AND (o.startDate < :endDate AND o.endDate > :startDate)
    """)
  boolean isAvailableBetween(Long carId, LocalDate startDate, LocalDate endDate);

  @Query("""
    SELECT o
    FROM Order o
    WHERE o.user.id = :userId
        AND (CURRENT_DATE between o.startDate AND o.endDate)
    """)
  List<Order> findActiveOrdersByUserId(Long userId);

  List<Order> findAllByCar_Id(long carId);

  @Query(
    """
    SELECT o
        FROM Order o JOIN Car car ON o.car.id = car.id JOIN Company c ON car.company.id = c.id
        WHERE c.id = :companyId
    """
  )
  List<Order> findAllOrdersByCompany_Id(Long companyId);
}
