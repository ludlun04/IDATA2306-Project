package no.ntnu.stud.idata2306_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import no.ntnu.stud.idata2306_project.model.order.Order;
import no.ntnu.stud.idata2306_project.repository.OrderRepository;
import no.ntnu.stud.idata2306_project.security.AccessUserDetails;
import no.ntnu.stud.idata2306_project.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
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
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    AccessUserDetails user = (AccessUserDetails) auth.getPrincipal();
    return ResponseEntity.ok(orderService.findOrdersByUserId(user.getId()));
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
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    AccessUserDetails user = (AccessUserDetails) auth.getPrincipal();
    return ResponseEntity.ok(orderService.findActiveOrdersByUserId(user.getId()));
  }
}
