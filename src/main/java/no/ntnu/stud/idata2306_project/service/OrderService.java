package no.ntnu.stud.idata2306_project.service;

import java.time.LocalDate;
import java.util.List;
import no.ntnu.stud.idata2306_project.model.order.Order;
import no.ntnu.stud.idata2306_project.repository.OrderRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for managing orders.
 */
@Service
public class OrderService {

  private final OrderRepository orderRepository;

  /**
   * Creates an instance of OrderService.
   *
   * @param orderRepository the order repository
   */
  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  /**
   * Check if the order is available from a given date.
   *
   * @param id the id of the order
   * @param startDate the date to check
   * @return true if the order is available from the given date, false otherwise
   */
  public boolean isAvailableFrom(Long id, LocalDate startDate) {
    return orderRepository.isAvailableFrom(id, startDate);
  }

  /**
   * Check if the order is available between two dates.
   *
   * @param id the id of the order
   * @param startDate the start date to check
   * @param endDate the end date to check
   * @return true if the order is available between the two dates, false otherwise
   */
  public boolean isAvailableBetween(Long id, LocalDate startDate, LocalDate endDate) {
    return orderRepository.isAvailableBetween(id, startDate, endDate);
  }

  /**
   * Get all active orders by user id
   *
   * @param userId the id of the user
   * @return a list of orders
   */
  public List<Order> findActiveOrdersByUserId(Long userId) {
    return orderRepository.findActiveOrdersByUserId(userId);
  }

  /**
   * Get all orders by user id
   *
   * @param userId the id of the user
   * @return a list of orders
   */
  public List<Order> findOrdersByUserId(Long userId) {
    return orderRepository.findOrdersByUserId(userId);
  }


  /**
   * Save an order
   *
   * @param order the order to save
   * @return the saved order
   */
  public void saveOrder(Order order) {
    orderRepository.save(order);
  }

  /**
   * Get an order by its id
   *
   * @param id the id of the order
   * @return the order with the given id
   */
  public Order findOrderById(Long id) {
    return orderRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
  }
}
