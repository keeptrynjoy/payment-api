package toy.paymentapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.paymentapi.domain.Item;
import toy.paymentapi.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
