package toy.paymentapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.paymentapi.domain.Item;
import toy.paymentapi.domain.PointHistory;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

}
