package toy.paymentapi.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.paymentapi.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
