package toy.paymentapi.order.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItemDto {
    private Long itemId;
    private Integer price;
    private Integer count;
}