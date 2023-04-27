package toy.paymentapi.order.domain;

import lombok.*;
import toy.paymentapi.order.service.dto.ItemDto;
import toy.paymentapi.support.error.ErrorCode;
import toy.paymentapi.support.error.PaymentApiException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static toy.paymentapi.support.error.ErrorCode.*;

@Entity
@Getter
@Table(name = "item_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Item(String name, int price, LocalDateTime registerDate, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.registerDate = registerDate;
        this.stockQuantity = stockQuantity;
    }

    //== 생성 메서드 ==//
    public static Item createItem(String name, int price, LocalDateTime registerDate, int stockQuantity){
        return Item.builder()
                .name(name)
                .price(price)
                .registerDate(registerDate)
                .stockQuantity(stockQuantity)
                .build();
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
            throw new PaymentApiException(NOT_ENOUGH_STOCK_QUANTITY);
        }

        this.stockQuantity = restStock;
    }

    public ItemDto toItemDto(){
        return ItemDto.builder()
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .registerDate(this.registerDate)
                .stockQuantity(this.stockQuantity)
                .build();
    }

    public static Item fromItemDto(ItemDto itemDto){
        return Item.createItem(itemDto.getName(),
                itemDto.getPrice(),
                LocalDateTime.now(),
                itemDto.getStockQuantity());
    }

}
