package toy.paymentapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.paymentapi.domain.Point;

public interface PointRepository extends JpaRepository<Point, Long> {

}
