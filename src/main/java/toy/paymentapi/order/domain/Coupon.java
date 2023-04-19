package toy.paymentapi.order.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "coupon_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id @GeneratedValue
    @Column(name = "coupon_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CouponType couponType;
    private int discountAmount;
    private LocalDateTime createDate;
    private Long itemId;

    @OneToMany(mappedBy = "coupon")
    private List<CouponIssue> couponIssues = new ArrayList<>();

    @Builder
    public Coupon(CouponType couponType, int discountAmount, LocalDateTime createDate, Long item) {
        this.couponType = couponType;
        this.discountAmount = discountAmount;
        this.createDate = createDate;
        this.itemId = item;
    }

    //== 연관관계 편의 메서드 ==//
    public void addCouponIssue(CouponIssue couponIssue){
        couponIssues.add(couponIssue);
        couponIssue.saveCoupon(this);
    }

    //== 생성 메서드 ==//
    /**
    * 쿠폰 발행
    **/
    public static Coupon createItemCoupon(CouponType couponType, int discountAmount,Long itemId){
        return Coupon.builder()
                .couponType(couponType)
                .discountAmount(discountAmount)
                .createDate(LocalDateTime.now())
                .item(itemId)
                .build();
    }


}
