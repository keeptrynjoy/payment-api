package toy.paymentapi.domain;

import lombok.*;
import toy.paymentapi.support.error.ErrorCode;
import toy.paymentapi.support.error.PaymentApiException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Entity
@Getter
@Table(name = "item_tb")
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private LocalDateTime registerDate;
    private int stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<Coupon> coupons = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    public Item(String name, int price, LocalDateTime registerDate, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.registerDate = registerDate;
        this.stockQuantity = stockQuantity;
    }

    //== 비즈니스 로직 ==//
    /**
    * stock 증가
    **/
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }


    /**
    * stock 감소
    **/
    public void removeStock(int quantity){

        int restStock = this.stockQuantity - quantity;

        if (restStock < 0) {
            throw ErrorCode.throwNotEnoughStockQuantity();
        }

        this.stockQuantity = restStock;
    }
}
