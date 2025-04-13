package no.ntnu.stud.idata2306_project.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import no.ntnu.stud.idata2306_project.model.order.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface OrderRepository extends ListCrudRepository<Order, Long> {
  public List<Order> findOrdersByUserId(Long userId);

  public Order findOrderByUserId(Long userId);

  @Query("""
    SELECT CASE WHEN COUNT(o) = 0 THEN true ELSE false END
    FROM Order o
    WHERE o.carId = :carId
        AND o.endDate <= :startDate
        AND NOT EXISTS (
            SELECT 1
            FROM Order o2
            WHERE o2.carId = :carId
              AND o2.startDate < :startDate
              AND o2.endDate >= :startDate
        )
    """)
boolean isAvailableFrom(Long carId, LocalDate startDate);

  @Query("""
    SELECT CASE WHEN COUNT(o) = 0 THEN true ELSE false END
    FROM Order o
    WHERE o.carId = :carId
        AND NOT EXISTS (
            SELECT 1
            FROM Order o2
            WHERE o2.carId = :carId
              AND o2.startDate < :endDate
              AND o2.endDate > :startDate
        )
    """)
  boolean isAvailableBetween(Long carId, LocalDate startDate, LocalDate endDate);
}
