package toy.paymentapi.domain;

import lombok.Getter;
import toy.paymentapi.domain.Enum.CouponType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "coupon_tb")
public class Coupon {

    @Id @GeneratedValue
    @Column(name = "coupon_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CouponType couponType;
    private int discountAmount;
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "coupon")
    private List<CouponIssue> couponIssues = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}
