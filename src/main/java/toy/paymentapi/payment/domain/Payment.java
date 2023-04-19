package toy.paymentapi.payment.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.paymentapi.order.domain.Order;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Table(name = "payment_tb")
@NoArgsConstructor
public class Payment {

    @Id @GeneratedValue
    @Column(name = "payment_id")
    private Long id;
    private int amount;
    private LocalDateTime LastChangeDate;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    private PgCompany pgCompany;
    private String buyerName;
    private String buyerEmail;
    private String buyerPhone;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
