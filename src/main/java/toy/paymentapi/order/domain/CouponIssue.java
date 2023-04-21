package toy.paymentapi.order.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Table(name = "coupon_issue")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CouponIssue {

    @Id @GeneratedValue
    @Column(name = "coupon_issue_id")
    private Long id;
    private LocalDateTime issueDate;
    private LocalDateTime expirationDate;

    @Enumerated(EnumType.STRING)
    private CouponIssueStatus useStatus;

    @Enumerated(EnumType.STRING)
    private CouponIssueReason changeReason;
    private LocalDateTime changeDate;
    private Long orderId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Builder
    public CouponIssue(LocalDateTime issueDate, LocalDateTime expirationDate, CouponIssueStatus useStatus, CouponIssueReason changeReason, LocalDateTime changeDate, Long orderId, Member member, Coupon coupon) {
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
        this.useStatus = useStatus;
        this.changeReason = changeReason;
        this.changeDate = changeDate;
        this.orderId = orderId;
        this.member = member;
        this.coupon = coupon;
    }

    //== 연관관계 편의 메서드 ==//
    public void saveCoupon(Coupon coupon){
        this.coupon = coupon;
    }


    //== 생성 메서드 ==//
    /**
    * 쿠폰 발급(회원)
    **/
    public static CouponIssue couponIssuedToMember(LocalDateTime expirationDate, Member member, Coupon coupon){
        return CouponIssue.builder()
                .issueDate(LocalDateTime.now())
                .expirationDate(expirationDate)
                .useStatus(CouponIssueStatus.ON)
                .member(member)
                .coupon(coupon)
                .build();
    }

    //== 조회 메서드 ==//
    public int getDiscountAmount(){
        return coupon.getDiscountAmount();
    }

    //== 수정 메서드 ==//
    public void updateCouponIssue(CouponIssueStatus useStatus,CouponIssueReason changeReason){
        CouponIssue.builder()
                .useStatus(useStatus)
                .changeReason(changeReason)
                .changeDate(LocalDateTime.now())
                .build();
    }
}
