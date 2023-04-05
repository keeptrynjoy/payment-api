package toy.paymentapi.domain;

import lombok.Getter;
import toy.paymentapi.domain.Enum.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Table(name = "order_tb")
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;
    private LocalDateTime orderDate;
    private int totalAmount;
    private int totalDiscount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<Payment> payment = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<UsedCoupon> usedCoupons = new ArrayList<>();
}
