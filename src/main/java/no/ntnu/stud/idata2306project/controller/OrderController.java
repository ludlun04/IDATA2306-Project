package no.ntnu.stud.idata2306project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import no.ntnu.stud.idata2306project.dto.OrderRequestDto;
import no.ntnu.stud.idata2306project.exception.OrderNotFoundException;
import no.ntnu.stud.idata2306project.model.car.Addon;
import no.ntnu.stud.idata2306project.model.car.Car;
import no.ntnu.stud.idata2306project.model.order.Order;
import no.ntnu.stud.idata2306project.model.user.User;
import no.ntnu.stud.idata2306project.repository.AddonRepository;
import no.ntnu.stud.idata2306project.security.AccessUserDetails;
import no.ntnu.stud.idata2306project.service.CarService;
import no.ntnu.stud.idata2306project.service.OrderService;
import no.ntnu.stud.idata2306project.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.InsufficientAuthenticationException;
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
  private final CarService carService;
  private final UserService userService;
  private final AddonRepository addonRepository;
  private final Logger logger = LoggerFactory.getLogger(OrderController.class);

  /**
   * Creates a new OrderController.
   *
   * @param orderService the order service to use
   * @param userService the user service to use
   * @param carService the car service to use
   * @param addonRepository the addon repository to use
   */
  public OrderController(OrderService orderService, UserService userService, CarService carService,
      AddonRepository addonRepository) {
    this.addonRepository = addonRepository;
    this.orderService = orderService;
    this.userService = userService;
    this.carService = carService;
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
      @ApiResponse(responseCode = "403", description = "Not authorized")
  })
  @PreAuthorize("hasAnyAuthority('USER')")
  @PostMapping("")
  public ResponseEntity<Long> addOrder(
      @Parameter(description = "User details of the logged-in user")
      @AuthenticationPrincipal AccessUserDetails accessUserDetails,
      @Parameter(description = "Order to add")
      @RequestBody OrderRequestDto orderDto
  ) {
    User user = this.userService.getUserById(accessUserDetails.getId());
    Optional<Car> optionalCar = this.carService.getCarById(orderDto.getCarId());

    if (!optionalCar.isPresent()) {
      logger.error("Car with id {} not found", orderDto.getCarId());
      return ResponseEntity.badRequest().build();
    }

    List<Addon> addons = orderDto.getAddonIds().stream()
        .map((Long addonId) -> {
          Optional<Addon> optionalAddon = this.addonRepository.findById(addonId);
          if (optionalAddon.isPresent()) {
            return optionalAddon.get();
          } else {
            return null;
          }
        }).toList();

    Order order = new Order();
    order.setUser(user);
    order.setCar(optionalCar.get());
    order.setStartDate(orderDto.getStartDate());
    order.setEndDate(orderDto.getEndDate());
    order.setAddons(addons);

    long numberOfDays = order.getEndDate().toEpochDay() - order.getStartDate().toEpochDay() + 1;
    if (numberOfDays <= 0) {
      logger.error("Start date is after end date");
      return ResponseEntity.badRequest().build();
    }
    long carPrice = order.getCar().getPricePerDay() * numberOfDays;

    long addonPrices = addons.stream()
        .mapToLong(Addon::getPrice)
        .sum();
    
    order.setPrice(carPrice + addonPrices);
    
    logger.info("Adding order {}", order);
    orderService.saveOrder(order);
    logger.info("New order added");
    return ResponseEntity.status(HttpStatus.CREATED).body(order.getOrderId());
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
  public ResponseEntity<Order> getOrderById(
      @Parameter(description = "The id of the order to get")
      @PathVariable Long id,
      @Parameter(description = "The user details of the logged-in user")
      @AuthenticationPrincipal AccessUserDetails user
  ) {
    // Check if the id is valid
    if (id == null || id <= 0) {
      logger.error("Invalid id: {}", id);
      return ResponseEntity.badRequest().build();
    }

    logger.info("Getting order with id {}", id);
    Order order = null;
    try {
      order = orderService.findOrderById(id);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }

    // Check if the order belongs to the user or they are admin
    boolean isAdmin = user.getAuthorities().stream()
        .anyMatch(predicate -> predicate.getAuthority().equals("ADMIN"));

    logger.info("User with id {} is admin: {}", user.getId(), isAdmin);
    boolean isOwnerOfOrder = order.getUser().getId().equals(user.getId());
    if (!isAdmin && !isOwnerOfOrder) {
      logger.error("User with id {} is not authorized to access order with id {}",
          user.getId(), id);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.ok(order);
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
      @ApiResponse(responseCode = "400", description = "Invalid id")
  })
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> removeOrder(
      @Parameter(description = "The id of the order to remove")
      @PathVariable Long id,
      @Parameter(description = "The user details of the logged-in user")
      @AuthenticationPrincipal AccessUserDetails user) {
    // Check if the id is valid
    if (id == null || id <= 0) {
      logger.error("Invalid id: {}", id);
      return ResponseEntity.badRequest().build();
    }

    logger.info("Removing order with id {}", id);
    Order order = null;
    try {
      order = orderService.findOrderById(id);
    } catch (OrderNotFoundException e) {
      logger.error("Order with id {} not found", id);
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }

    // Check if the order belongs to the user or they are admin
    boolean isAdmin = user.isAdmin();

    logger.info("User with id {} is admin: {}", user.getId(), isAdmin);

    boolean isOwnerOfOrder = order.getUser().getId().equals(user.getId());
    if (!isAdmin && !isOwnerOfOrder) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    orderService.deleteOrderById(id);

    return ResponseEntity.ok("Order removed");
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
  public ResponseEntity<List<Order>> getOrdersByCarId(
      @Parameter(description = "The id of the car to get orders for")
      @PathVariable Long carId
  ) {
    logger.info("Getting orders for car with id {}", carId);
    return ResponseEntity.ok(orderService.getOrdersByCarId(carId));
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
      @ApiResponse(responseCode = "404", description = "Company not found"),
      @ApiResponse(responseCode = "500", description = "Unknown error")
  })
  @GetMapping("company/{companyId}")
  @PreAuthorize("hasAnyAuthority('USER')")
  public ResponseEntity<List<Order>> getOrdersByCompanyId(
      @PathVariable Long companyId,
      @AuthenticationPrincipal AccessUserDetails accessUserDetails
  ) {
    logger.info("Getting orders for company with id {}", companyId);
    User user = this.userService.getUserById(accessUserDetails.getId());
    try {
      return ResponseEntity.ok(orderService.getOrdersByCompanyId(companyId, user.getId()));
    } catch (InsufficientAuthenticationException e) {
      logger.info("User with id {} does not belong to company with id {}", user.getId(), companyId);
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
