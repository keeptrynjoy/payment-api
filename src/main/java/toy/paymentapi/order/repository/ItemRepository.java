package toy.paymentapi.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import toy.paymentapi.order.domain.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(value = "SELECT i FROM Item i WHERE i.id = :itemId")
    List<Item> findByItemList(@Param("itemId") List<Long> itemId);
    Page<Item> findAll(Pageable pageable);
}
