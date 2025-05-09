package no.ntnu.stud.idata2306project.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import no.ntnu.stud.idata2306project.exception.OrderNotFoundException;

import no.ntnu.stud.idata2306project.model.company.Company;
import no.ntnu.stud.idata2306project.model.order.Order;
import no.ntnu.stud.idata2306project.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Service;

/**
 * Service class for managing orders.
 */
@Service
public class OrderService {

  private final OrderRepository orderRepository;
  private final CompanyService companyService;
  private final Logger logger = LoggerFactory.getLogger(OrderService.class);

  /**
   * Creates an instance of OrderService.
   *
   * @param orderRepository the order repository
   */
  public OrderService(OrderRepository orderRepository, CompanyService companyService) {
    this.orderRepository = orderRepository;
    this.companyService = companyService;
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
        .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
  }

  /**
   * Delete an order by its id
   *
   * @param id the id of the order
   */
  public void deleteOrderById(Long id) throws OrderNotFoundException {
    orderRepository.deleteById(id);
  }

  public List<Order> getOrdersByCarId(Long carId) {
    return orderRepository.findAllByCar_Id(carId);
  }

  public List<Order> getOrdersByCompanyId(Long companyId, Long userId) {
    Company company = companyService.getCompanyById(companyId);
    if (company == null) {
      logger.error("Company with id {} not found", companyId);
      throw new IllegalArgumentException("Company with id " + companyId + " not found");
    }
    boolean userBelongsToCompany = company.getUsers().stream()
        .anyMatch(user -> Objects.equals(user.getId(), userId));
    if (!userBelongsToCompany) {
      logger.error("User with id {} does not belong to company with id {}", userId, companyId);
      throw new InsufficientAuthenticationException("User with id " + userId + " does not belong to company with id " + companyId);
    }
    return orderRepository.findAllOrdersByCompany_Id(companyId);
  }
}
