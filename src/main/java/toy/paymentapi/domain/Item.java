package toy.paymentapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "item_tb")
@NoArgsConstructor
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private LocalDateTime registerDate;
    private int stockQuantity;

    public Item(String name, int price, LocalDateTime registerDate, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.registerDate = registerDate;
        this.stockQuantity = stockQuantity;
    }

    @OneToMany(mappedBy = "item")
    private List<Coupon> coupons = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();
}
