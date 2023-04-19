package toy.paymentapi.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.paymentapi.order.domain.Coupon;
import toy.paymentapi.order.domain.CouponIssue;

public interface CouponIssueRepository extends JpaRepository<CouponIssue, Long> {
}
