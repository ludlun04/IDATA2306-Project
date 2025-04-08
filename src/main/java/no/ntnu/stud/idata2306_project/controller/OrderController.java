package no.ntnu.stud.idata2306_project.controller;

import java.util.List;
import no.ntnu.stud.idata2306_project.model.order.Order;
import no.ntnu.stud.idata2306_project.repository.OrderRepository;
import no.ntnu.stud.idata2306_project.security.AccessUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Orders", description = "Endpoints for managing orders")
@RestController
@RequestMapping("/order")
public class OrderController {
  private final OrderRepository orderRepository;

  public OrderController(OrderRepository orderRepository) {
      this.orderRepository = orderRepository;
  }

  @GetMapping("/history")
  public ResponseEntity<List<Order>> getCompanyOrders() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    AccessUserDetails user = (AccessUserDetails) auth.getPrincipal();
    return ResponseEntity.ok(orderRepository.findOrdersByUserId(user.getId()));
  }

  /**
  @GetMapping("/{userId}")
  public ResponseEntity<List<Order>> getUserOrders(SecurityContext securityContext) {
  }
  */
}
