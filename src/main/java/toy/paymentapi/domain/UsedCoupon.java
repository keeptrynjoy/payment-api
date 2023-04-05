package toy.paymentapi.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Table(name = "used_coupon_tb")
public class UsedCoupon {

    @Id @GeneratedValue
    @Column(name="used_coupon_id")
    private Long id;
    private String reason;
    private LocalDateTime writeDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "coupon_issue_id")
    private CouponIssue couponIssue;
}
