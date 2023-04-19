package toy.paymentapi.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.paymentapi.order.domain.Point;

public interface PointRepository extends JpaRepository<Point, Long> {

}
