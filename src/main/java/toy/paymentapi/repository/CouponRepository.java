package toy.paymentapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.paymentapi.domain.Coupon;
import toy.paymentapi.domain.Item;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
