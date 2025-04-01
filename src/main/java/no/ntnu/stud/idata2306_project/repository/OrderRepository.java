package no.ntnu.stud.idata2306_project.repository;

import no.ntnu.stud.idata2306_project.model.order.Order;
import org.springframework.data.repository.ListCrudRepository;

public interface OrderRepository extends ListCrudRepository<Order, Long> {
}
