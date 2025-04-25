package no.ntnu.stud.idata2306_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import no.ntnu.stud.idata2306_project.model.order.Order;
import no.ntnu.stud.idata2306_project.security.AccessUserDetails;
import no.ntnu.stud.idata2306_project.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Represents a controller for orders.
 *
 * <p>
 * Contains the following endpoints:
 * <ul>
 * <li>GET /order/history: Get all orders for the logged in user</li>
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

  /**
   * Get a order by its id.
   * 
   * @param id the id of the order to get
   * @return the order with the given id
   */
  @Operation(summary = "Get a order by its id", description = "Get a order by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Order with the given id"),
      @ApiResponse(responseCode = "404", description = "Order not found"),
      @ApiResponse(responseCode = "403", description = "Not authorized"),
      @ApiResponse(responseCode = "400", description = "Invalid id")
  })
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    AccessUserDetails user = (AccessUserDetails) auth.getPrincipal();

    // Check if the id is valid
    if (id == null || id <= 0) {
      logger.error("Invalid id: {}", id);
      return ResponseEntity.badRequest().build();
    }

    logger.info("Getting order with id {}", id);
    Order order = orderService.findOrderById(id);
    if (order == null) {
      logger.error("Order with id {} not found", id);
      return ResponseEntity.notFound().build();
    }

    // Check if the order belongs to the user or they are admin
    boolean isAdmin = user.getAuthorities().stream()
        .anyMatch(predicate -> predicate.getAuthority().equals("ADMIN"));

    logger.info("User with id {} is admin: {}", user.getId(), isAdmin);
    boolean isOwnerOfOrder = order.getUserId().equals(user.getId());
    if (!isAdmin && !isOwnerOfOrder) {
      logger.error("User with id {} is not authorized to access order with id {}", user.getId(), id);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.ok(order);
  }
}
