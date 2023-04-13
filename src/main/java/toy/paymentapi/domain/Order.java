package toy.paymentapi.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.paymentapi.domain.Enum.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Table(name = "order_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<CouponIssue> couponIssues = new ArrayList<>();

    @Builder
    public Order(LocalDateTime orderDate, int totalAmount, int totalDiscount, OrderStatus status, Member member) {
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.totalDiscount = totalDiscount;
        this.status = status;
        this.member = member;
    }

    //== 연관 관계 메서드 ==//
    public void ownerMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //== 비즈니스 로직 ==//
    /**
    * 쿠폰 금액, 사용 포인트 합산
    **/
    public void calculateDiscount(CouponIssue useCoupon, int usePoint){
        int couponDiscount = useCoupon.getCoupon().getDiscountAmount();
        this.totalDiscount = couponDiscount+usePoint;
    }

    /**
     * 전체 주문 가격 합산
     * */
    public void getTotalAmount(){
        this.totalAmount = orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }

    //== 생성 메서드 ==//
    public static Order createOrder(Member member, CouponIssue useCoupon,int usePoint ,List<OrderItem> orderItems){
        Order order = Order.builder()
                        .status(OrderStatus.ORDER)
                        .orderDate(LocalDateTime.now())
                        .build();

        order.ownerMember(member);

        //주문 상품들을 list 에 저장
        orderItems.forEach(order::addOrderItem);

        //주문 상품들의 상품 합계 금액을 합산
        order.getTotalAmount();

        order.calculateDiscount(useCoupon,usePoint);

        return order;
    }


}
