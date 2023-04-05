package toy.paymentapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.paymentapi.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
