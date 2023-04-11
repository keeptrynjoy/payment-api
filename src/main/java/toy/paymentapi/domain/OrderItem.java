package toy.paymentapi.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Optional;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Table(name ="order_item_tb")
@NoArgsConstructor
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;
    private int orderPrice;
    private int count;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(Item item, int orderPrice, int count) {
        this.orderPrice = orderPrice;
        this.count = count;
        this.item = item;
    }

    public void setOrder(Order order){
        this.order = order;
    }


    //== 생성 메서드 ==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        return new OrderItem(item,orderPrice,count);
    }

    //== 비즈니스 로직 ==//
    /**
    * 주문 취소시 재고 차감
    **/
    public void cancel(){
        getItem().addStock(count);
    }

    /**
    * 상품 합계 금액
    **/
    public int getTotalPrice(){
        return getOrderPrice() * getCount();
    }
}
