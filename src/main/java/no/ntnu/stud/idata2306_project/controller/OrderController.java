package no.ntnu.stud.idata2306_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import no.ntnu.stud.idata2306_project.model.order.Order;
import no.ntnu.stud.idata2306_project.repository.OrderRepository;
import no.ntnu.stud.idata2306_project.security.AccessUserDetails;
import no.ntnu.stud.idata2306_project.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Represents a controller for orders.
 *
 * <p>Contains the following endpoints:
 * <ul>
 *   <li>GET /order/history: Get all orders for the logged in user</li>
 * </ul>
 */
@Tag(name = "Orders", description = "Endpoints for managing orders")
@RestController
@RequestMapping("/order")
public class OrderController {
  private final OrderService orderService;
  private Logger logger = LoggerFactory.getLogger(OrderController.class);

  /**
   * Creates a new OrderController.
   *
   * @param orderService the order service to use
   */
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  /**
   * Get all orders for the logged in user.
   *
   * @return a list of all orders for the logged in user
   */
  @Operation(summary = "Get all orders for the logged in user", description = "Get a list of all orders for the logged in user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of orders")
  })
  @GetMapping("/history")
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  public ResponseEntity<List<Order>> getAuthenticatedUserOrders() {
    try {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      AccessUserDetails user = (AccessUserDetails) auth.getPrincipal();
      logger.info("User found with ID: {}", user.getId());
      logger.info("Getting orders for user with ID: {}", user.getId());
      return ResponseEntity.ok(orderService.findOrdersByUserId(user.getId()));
    } catch (ClassCastException e) {
      logger.error("Error casting authentication principal to AccessUserDetails", e);
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Get all active orders for the logged in user.
   *
   * @return a list of all active orders for the logged in user
   */
  @Operation(summary = "Get all active orders for the logged in user", description = "Get a list of all active orders for the logged in user")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "List of active orders")
  })
  @GetMapping("/active")
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  public ResponseEntity<List<Order>> getAuthenticatedUserActiveOrders() {
    try {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      AccessUserDetails user = (AccessUserDetails) auth.getPrincipal();
      logger.info("User found with ID: {}", user.getId());
      logger.info("Getting active orders for user with ID: {}", user.getId());
      return ResponseEntity.ok(orderService.findActiveOrdersByUserId(user.getId()));
    } catch (ClassCastException e) {
      logger.error("Error casting authentication principal to AccessUserDetails", e);
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Add a new order.
   *
   * @param order the order to add
   * @return the order that was added
   */
  @Operation(summary = "Add a new order", description = "Add a new order")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Order that was added")
  })
  @PreAuthorize("hasAnyAuthority('USER')")
  @PostMapping("/create")
  public ResponseEntity<Order> addOrder(Order order) {
      logger.info("Adding order {}", order);
      orderService.saveOrder(order);
      logger.info("New order added");
      return ResponseEntity.status(201).body(order);
  }
}
