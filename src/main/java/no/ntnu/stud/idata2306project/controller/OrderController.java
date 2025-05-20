package no.ntnu.stud.idata2306project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import no.ntnu.stud.idata2306project.dto.OrderRequestDto;
import no.ntnu.stud.idata2306project.dto.OrderResponseDto;
import no.ntnu.stud.idata2306project.exception.CarNotFoundException;
import no.ntnu.stud.idata2306project.exception.InvalidDatesException;
import no.ntnu.stud.idata2306project.exception.OrderNotFoundException;
import no.ntnu.stud.idata2306project.exception.UnauthorizedException;
import no.ntnu.stud.idata2306project.exception.UserNotFoundException;
import no.ntnu.stud.idata2306project.security.AccessUserDetails;
import no.ntnu.stud.idata2306project.service.OrderService;
import no.ntnu.stud.idata2306project.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represents a controller for orders.
 *
 * <p>Contains the following endpoints:
 * <ul>
 * <li> Get all orders for the logged in user
 * <li> Get all active orders for the logged in user
 * <li> Add a new order
 * <li> Get a order by its id
 * <li> Remove an order by its id
 * <li> Get all orders for a car
 * <li> Get all orders for a company
 * </ul>
 */
@Tag(name = "Orders", description = "Endpoints for managing orders")
@RestController
@RequestMapping("/order")
public class OrderController {
  private final OrderService orderService;
  private final Logger logger = LoggerFactory.getLogger(OrderController.class);

  /**
   * Creates a new OrderController.
   *
   * @param orderService the order service to use
   */
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  /**
   * Get all orders for the logged-in user.
   *
   * @return a list of all orders for the logged-in user
   */
  @Operation(
      summary = "Get all orders for the logged in user",
      description = "Get a list of all orders for the logged in user"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of orders"),
      @ApiResponse(responseCode = "403", description = "Not authorized"),
      @ApiResponse(responseCode = "400", description = "Invalid user information")
  })
  @GetMapping("/history")
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  public ResponseEntity<List<OrderResponseDto>> getAuthenticatedUserOrders() {
    logger.info("Getting orders for authenticated user");
    try {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      AccessUserDetails user = (AccessUserDetails) auth.getPrincipal();
      logger.info("User found with ID: {}", user.getId());
      return ResponseEntity.ok(orderService.findOrdersByUserId(user.getId()));
    } catch (ClassCastException e) {
      logger.error("Error casting authentication principal to AccessUserDetails", e);
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Get all active orders for the logged-in user.
   *
   * @return a list of all active orders for the logged-in user
   */
  @Operation(
      summary = "Get all active orders for the logged in user",
      description = "Get a list of all active orders for the logged in user"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of active orders"),
      @ApiResponse(responseCode = "403", description = "Not authorized"),
      @ApiResponse(responseCode = "400", description = "Invalid user information")
  })
  @GetMapping("/active")
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  public ResponseEntity<List<OrderResponseDto>> getAuthenticatedUserActiveOrders() {
    logger.info("Getting active orders for authenticated user");
    try {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      AccessUserDetails user = (AccessUserDetails) auth.getPrincipal();
      logger.info("User found with ID: {}", user.getId());
      return ResponseEntity.ok(orderService.findActiveOrdersByUserId(user.getId()));
    } catch (ClassCastException e) {
      logger.error("Error casting authentication principal to AccessUserDetails", e);
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Add a new order.
   *
   * @param accessUserDetails the user details of the logged-in user
   * @param orderDto the order to add
   * @return the order that was added
   */
  @Operation(
      summary = "Add a new order",
      description = "Add a new order. Can only be done by a user."
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Order that was added"),
      @ApiResponse(responseCode = "400", description = "Invalid order"),
      @ApiResponse(responseCode = "403", description = "Not authorized"),
      @ApiResponse(responseCode = "500", description = "Unknown error"),
  })
  @PreAuthorize("hasAnyAuthority('USER')")
  @PostMapping("")
  public ResponseEntity<Long> addOrder(
      @Parameter(description = "User details of the logged-in user")
      @AuthenticationPrincipal AccessUserDetails accessUserDetails,
      @Parameter(description = "Order to add")
      @RequestBody OrderRequestDto orderDto
  ) {
    long userId = accessUserDetails.getId();

    try {
      long id = orderService.addOrder(userId, orderDto);
      return ResponseEntity.status(HttpStatus.CREATED).body(id);
    } catch (CarNotFoundException e) {
      logger.error("Car not found: {}", e.getMessage());
      return ResponseEntity.badRequest().build();
    } catch (InvalidDatesException e) {
      logger.error("Invalid dates: {}", e.getMessage());
      return ResponseEntity.notFound().build();
    } catch (UserNotFoundException e) {
      logger.error("User not found: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    } catch (Exception e) {
      logger.error("Unknown error: {}", e.getMessage());
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
   * Get an order by its id.
   *
   * @param id the id of the order to get
   * @return the order with the given id
   */
  @Operation(summary = "Get an order by its id", description = "Get a order by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Order with the given id"),
      @ApiResponse(responseCode = "404", description = "Order not found"),
      @ApiResponse(responseCode = "403", description = "Not authorized"),
      @ApiResponse(responseCode = "401", description = "Not authorized for this resource"),
      @ApiResponse(responseCode = "400", description = "Invalid id"),
      @ApiResponse(responseCode = "500", description = "Unknown error")
  })
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<OrderResponseDto> getOrderById(
      @Parameter(description = "The id of the order to get")
      @PathVariable Long id,
      @Parameter(description = "The user details of the logged-in user")
      @AuthenticationPrincipal AccessUserDetails user
  ) {
    logger.info("Getting order with id {}", id);
    if (id == null || id <= 0) {
      logger.error("Invalid id: {}", id);
      return ResponseEntity.badRequest().build();
    }

    try {
      if (this.orderService.userHasAccessToOrder(user, id)) {
        logger.info("User with id {} has access to order with id {}", user.getId(), id);
        return ResponseEntity.ok(orderService.findOrderById(id));
      } else {
        logger.error("User with id {} does not have access to order with id {}", user.getId(), id);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
    } catch (OrderNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e) {
      logger.error("Unknown error: {}", e.getMessage());
      return ResponseEntity.internalServerError().build();
    }

  }
  

  /**
   * Removes an order by its id.
   *
   * @param id the id of the order to remove
   */
  @Operation(summary = "Remove an order by its id", description = "Remove an order by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Order removed"),
      @ApiResponse(responseCode = "404", description = "Order not found"),
      @ApiResponse(responseCode = "403", description = "Not authorized"),
      @ApiResponse(responseCode = "400", description = "Invalid id"),
      @ApiResponse(responseCode = "500", description = "Unknown error")
  })
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> removeOrder(
      @Parameter(description = "The id of the order to remove")
      @PathVariable Long id,
      @Parameter(description = "The user details of the logged-in user")
      @AuthenticationPrincipal AccessUserDetails user) {
    logger.info("Removing order with id {}", id);
    if (id == null || id <= 0) {
      logger.error("Invalid id: {}", id);
      return ResponseEntity.badRequest().build();
    }

    try {
      if (this.orderService.userHasAccessToOrder(user, id)) {
        logger.info("User with id {} has access to order with id {}", user.getId(), id);
        orderService.deleteOrderById(id);
        return ResponseEntity.ok("Order removed");
      } else {
        logger.error("User with id {} does not have access to order with id {}", user.getId(), id);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
    } catch (OrderNotFoundException e) {
      logger.error("Order with id {} not found", id);
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
   * Get all orders for a car.
   *
   * @param carId the id of the car to get orders for
   * @return a list of orders for the car
   */
  @Operation(summary = "Get all orders for a car", description = "Get all orders for a car")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of orders for the car"),
  })
  @GetMapping("car/{carId}")
  public ResponseEntity<List<OrderResponseDto>> getOrdersByCarId(
      @Parameter(description = "The id of the car to get orders for")
      @PathVariable Long carId
  ) {
    logger.info("Getting orders for car with id {}", carId);
    List<OrderResponseDto> orders = orderService.getOrdersByCarId(carId);
    for (OrderResponseDto order : orders) {
      logger.info("Order with id {} found for car with id {}", order.getOrderId(), carId);
    }
    return ResponseEntity.ok(orders);
  }

  /**
   * Get all orders for a company. User must belong to company.
   *
   * @param companyId the id of the company to get orders for
   * @param accessUserDetails the user details of the logged-in user
   * @return a list of orders for the company
   */
  @Operation(
      summary = "Get all orders for a company",
      description = "Get all orders for a company."
          + "Can only be accessed by users belonging to the company"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of orders for the company"),
      @ApiResponse(responseCode = "403", description = "Not authorized"),
      @ApiResponse(responseCode = "401", description = "Not authorized for this resource"),
      @ApiResponse(responseCode = "404", description = "Company not found"),
      @ApiResponse(responseCode = "500", description = "Unknown error")
  })
  @GetMapping("company/{companyId}")
  @PreAuthorize("hasAnyAuthority('USER')")
  public ResponseEntity<List<OrderResponseDto>> getOrdersByCompanyId(
      @PathVariable Long companyId,
      @AuthenticationPrincipal AccessUserDetails accessUserDetails
  ) {
    logger.info("Getting orders for company with id {}", companyId);
    try {
      return ResponseEntity.ok(orderService.getOrdersByCompanyId(companyId, accessUserDetails.getId()));
    } catch (UnauthorizedException e) {
      logger.info("User with id {} does not belong to company with id {}", accessUserDetails.getId(), companyId);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    } catch (IllegalArgumentException e) {
      logger.error("Company with id {} not found", companyId);
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      logger.error("Unknown error encountered while getting orders for company with id {}. {}",
          companyId, e.getMessage());
      return ResponseEntity.internalServerError().build();
    }
  }
}
