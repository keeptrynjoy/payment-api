package toy.paymentapi.order.domain;

import lombok.*;
import toy.paymentapi.payment.domain.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "point_his_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point {

    @Id @GeneratedValue
    @Column(name = "point_his_id")
    private Long id;
    private int afterPoint;
    private int calculationPoint;
    private int beforePoint;
    @Enumerated(EnumType.STRING)
    private PointReason reason;
    private LocalDateTime writeDate;
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void ownerMember(Member member){
        this.member = member;
        member.getPoints().add(this);
    }

    @Builder
    public Point(int afterPoint, int calculationPoint, int beforePoint, PointReason reason, LocalDateTime writeDate, Long orderId) {
        this.afterPoint = afterPoint;
        this.calculationPoint = calculationPoint;
        this.beforePoint = beforePoint;
        this.reason = reason;
        this.writeDate = writeDate;
        this.orderId = orderId;
    }

    //    public Point(int afterPoint, int calculationPoint, int beforePoint, String reason, LocalDateTime writeDate, Long orderId) {
//        this.afterPoint = afterPoint;
//        this.calculationPoint = calculationPoint;
//        this.beforePoint = beforePoint;
//        this.reason = reason;
//        this.writeDate = writeDate;
//        this.OrderId = orderId;
//    }

    public static Point createPointByOrder(
            int afterPoint, int usePoint, Long orderId,Member member)
    {

        Point point = Point.builder()
                .afterPoint(afterPoint)
                .calculationPoint(usePoint)
                .beforePoint(afterPoint-usePoint)
                .reason(PointReason.ORDER)
                .writeDate(LocalDateTime.now())
                .orderId(orderId)
                .build();

        point.ownerMember(member);

        return point;
    }

    public static Point createPointBySignUp(Member member)
    {

        Point point = Point.builder()
                .afterPoint(1000)
                .reason(PointReason.WELCOME)
                .writeDate(LocalDateTime.now())
                .build();

        point.ownerMember(member);

        return point;
    }
}
