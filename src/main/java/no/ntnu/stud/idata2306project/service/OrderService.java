package no.ntnu.stud.idata2306project.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import no.ntnu.stud.idata2306project.dto.CarDto;
import no.ntnu.stud.idata2306project.dto.OrderRequestDto;
import no.ntnu.stud.idata2306project.dto.OrderResponseDto;
import no.ntnu.stud.idata2306project.exception.CarNotFoundException;
import no.ntnu.stud.idata2306project.exception.InvalidDatesException;
import no.ntnu.stud.idata2306project.exception.OrderNotFoundException;
import no.ntnu.stud.idata2306project.exception.UnauthorizedException;
import no.ntnu.stud.idata2306project.model.car.Addon;
import no.ntnu.stud.idata2306project.model.car.Car;
import no.ntnu.stud.idata2306project.model.company.Company;
import no.ntnu.stud.idata2306project.model.order.Order;
import no.ntnu.stud.idata2306project.model.user.User;
import no.ntnu.stud.idata2306project.repository.OrderRepository;
import no.ntnu.stud.idata2306project.security.AccessUserDetails;
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
  private final UserService userService;
  private final CarService carService;
  private final AddonService addonService;
  private final Logger logger = LoggerFactory.getLogger(OrderService.class);

  /**
   * Creates an instance of OrderService.
   *
   * @param orderRepository the order repository
   */
  public OrderService(OrderRepository orderRepository, CompanyService companyService,
      UserService userService, CarService carService, AddonService addonService) {
    this.orderRepository = orderRepository;
    this.companyService = companyService;
    this.userService = userService;
    this.carService = carService;
    this.addonService = addonService;
  }

  /**
   * Check if the order is available from a given date.
   *
   * @param id        the id of the order
   * @param startDate the date to check
   * @return true if the order is available from the given date, false otherwise
   */
  public boolean isAvailableFrom(Long id, LocalDate startDate) {
    logger.trace("Checking if order with id: {} is available from date: {}", id, startDate);
    return orderRepository.isAvailableFrom(id, startDate);
  }

  /**
   * Check if the order is available between two dates.
   *
   * @param carid     the id of the car to check
   * @param startDate the start date to check
   * @param endDate   the end date to check
   * @return true if the order is available between the two dates, false otherwise
   */
  public boolean isAvailableBetween(Long carid, LocalDate startDate, LocalDate endDate) {
    logger.trace("Checking if car with id: {} is available between dates: {} and {}", carid,
        startDate, endDate);
    return orderRepository.isAvailableBetween(carid, startDate, endDate);
  }

  /**
   * Get all active orders by user id.
   *
   * @param userId the id of the user
   * @return a list of orders
   */
  public List<OrderResponseDto> findActiveOrdersByUserId(Long userId) {
    logger.trace("Finding active orders for user with id: {}", userId);
    List<Order> orders = orderRepository.findActiveOrdersByUserId(userId);
    return getOrderResponseDtos(orders);
  }

  /**
   * Get all orders by user id.
   *
   * @param userId the id of the user
   * @return a list of orders
   */
  public List<OrderResponseDto> findOrdersByUserId(Long userId) {
    logger.trace("Finding orders for user with id: {}", userId);
    return getOrderResponseDtos(orderRepository.findOrdersByUserId(userId));
  }

  /**
   * Save an order.
   *
   * @param order the order to save
   */
  private void saveOrder(Order order) {
    logger.trace("Saving order: {}", order);
    orderRepository.save(order);
  }

  /**
   * Get an order by its id.
   *
   * @param id the id of the order
   * @return the order with the given id
   */
  public OrderResponseDto findOrderById(Long id) {
    logger.trace("Finding order with id: {}", id);
    Order order = orderRepository.findById(id)
        .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

    return getOrderResponseDto(order);
  }

  /**
   * Delete an order by its id.
   *
   * @param id the id of the order
   */
  public void deleteOrderById(Long id) throws OrderNotFoundException {
    logger.trace("Deleting order with id: {}", id);
    orderRepository.deleteById(id);
  }

  /**
   * Get all orders belonging to a car.
   *
   * @param carId the id of the car
   * @return a list of orders belonging to the car
   */
  public List<OrderResponseDto> getOrdersByCarId(Long carId) {
    logger.trace("Finding orders with car id: {}", carId);
    List<Order> orders = orderRepository.findAllByCar_Id(carId);

    return getOrderResponseDtos(orders);
  }

  /**
   * Get all orders belonging to a company.
   *
   * @param companyId the id of the company
   * @param userId    id of user requesting the orders
   * @return a list of orders belonging to the company
   */
  public List<OrderResponseDto> getOrdersByCompanyId(Long companyId, Long userId)
      throws IllegalArgumentException, InsufficientAuthenticationException {
    logger.trace("Finding orders with company id: {}", companyId);
    Company company = companyService.getCompanyById(companyId);
    if (company == null) {
      throw new IllegalArgumentException("Company with id " + companyId + " not found");
    }
    boolean userBelongsToCompany = company.getUsers().stream()
        .anyMatch(user -> Objects.equals(user.getId(), userId));
    if (!userBelongsToCompany) {
      throw new UnauthorizedException(
          "User with id " + userId + " does not belong to company with id " + companyId);
    }
    return getOrderResponseDtos(orderRepository.findAllOrdersByCompany_Id(companyId));
  }

  /**
   * Adds an order.
   *
   * @param userId  the id of the user
   * @param orderDto the order to add
   * @return the id of the order that was added
   */
  public Long addOrder(long userId, OrderRequestDto orderDto) {
    logger.trace("Adding order for user with id: {}", userId);
    User user = this.userService.getUserById(userId);
    Optional<Car> optionalCar = this.carService.getCarById(orderDto.getCarId());

    if (optionalCar.isEmpty()) {
      throw new CarNotFoundException("Car not found with id: " + orderDto.getCarId());
    }

    if (!isAvailableBetween(orderDto.getCarId(), orderDto.getStartDate(), orderDto.getEndDate())) {
      throw new InvalidDatesException("Car is not available between the given dates");
    }

    List<Addon> addons = orderDto.getAddonIds().stream()
        .map((Long addonId) -> {
          Optional<Addon> optionalAddon = this.addonService.findById(addonId);
          return optionalAddon.orElse(null);
        }).toList();

    Order order = new Order();
    order.setUser(user);
    order.setCar(optionalCar.get());
    order.setStartDate(orderDto.getStartDate());
    order.setEndDate(orderDto.getEndDate());
    order.setAddons(addons);

    long numberOfDays = order.getEndDate().toEpochDay() - order.getStartDate().toEpochDay() + 1;
    if (numberOfDays <= 0) {
      throw new InvalidDatesException("Start date is after end date");
    }
    long carPrice = order.getCar().getPricePerDay() * numberOfDays;

    long addonPrices = addons.stream()
        .mapToLong(Addon::getPrice)
        .sum();

    order.setPrice(carPrice + addonPrices);
    saveOrder(order);
    return order.getOrderId();
  }

  /**
   * Check if a user has access to an order.
   *
   * @param user the user to check
   * @param orderId the id of the order
   * @return true if the user has access to the order, false otherwise
   */
  public boolean userHasAccessToOrder(AccessUserDetails user, long orderId) {
    logger.trace("Checking if user with id: {} has access to order with id: {}", user.getId(),
        orderId);
    OrderResponseDto order = findOrderById(orderId);

    boolean isAdmin = user.getAuthorities().stream()
        .anyMatch(predicate ->
            predicate.getAuthority().equals("ADMIN"));
    boolean isOwnerOfOrder = order.getUser().getId().equals(user.getId());

    return isAdmin || isOwnerOfOrder;

  }

  private List<OrderResponseDto> getOrderResponseDtos(List<Order> orders) {
    return orders.stream()
        .map(order -> new OrderResponseDto(order, carService.getCarDtoFromCar(order.getCar())))
        .toList();
  }

  private OrderResponseDto getOrderResponseDto(Order order) {
    CarDto carDto = carService.getCarDtoFromCar(order.getCar());
    return new OrderResponseDto(order, carDto);
  }
}
