package toy.paymentapi.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.paymentapi.order.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
