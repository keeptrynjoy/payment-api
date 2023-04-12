package toy.paymentapi.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import toy.paymentapi.domain.Enum.PointReason;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter
@Table(name = "point_his_tb")
@NoArgsConstructor
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

        Point point = new Point();
        point.afterPoint=afterPoint;
        point.calculationPoint=usePoint;
        point.beforePoint=afterPoint-usePoint;
        point.reason=PointReason.ORDER;
        point.writeDate=LocalDateTime.now();
        point.orderId=orderId;

        point.ownerMember(member);

        return point;
    }
}
