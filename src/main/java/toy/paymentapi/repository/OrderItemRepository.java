package toy.paymentapi.repository;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import toy.paymentapi.domain.Item;
import toy.paymentapi.domain.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
