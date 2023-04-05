package toy.paymentapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.paymentapi.domain.Item;
import toy.paymentapi.domain.UsedCoupon;

public interface UsedCouponRepository extends JpaRepository<UsedCoupon, Long> {

}
