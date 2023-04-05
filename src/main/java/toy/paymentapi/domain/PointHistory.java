package toy.paymentapi.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "point_his_tb")
public class PointHistory {

    @Id @GeneratedValue
    @Column(name = "point_his_id")
    private Long id;
    private int afterPoint;
    private int calculationPoint;
    private int beforePoint;
    private String reason;
    private LocalDateTime writeDate;
    private Long OrderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;
}
