package toy.paymentapi.domain;

import lombok.Getter;
import toy.paymentapi.domain.Enum.CouponIssueStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Table(name = "coupon_issue")
public class CouponIssue {

    @Id @GeneratedValue
    @Column(name = "coupon_issue_id")
    private Long id;
    private LocalDateTime issueDate;
    private LocalDateTime expirationDate;

    @Enumerated(EnumType.STRING)
    private CouponIssueStatus useStatus;

    private String changeReason;
    private LocalDateTime changeDate;
    private Long orderId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;



}
