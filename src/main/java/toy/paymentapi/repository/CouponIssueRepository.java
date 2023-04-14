package toy.paymentapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.paymentapi.domain.CouponIssue;
import toy.paymentapi.domain.Item;

public interface CouponIssueRepository extends JpaRepository<CouponIssue, Long> {
}
