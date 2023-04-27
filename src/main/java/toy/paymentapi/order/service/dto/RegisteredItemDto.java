package toy.paymentapi.order.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import toy.paymentapi.order.controller.response.ItemInfoResponse;
import toy.paymentapi.order.domain.Item;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisteredItemDto {

    private final Long itemId;
    private final String name;
    private final Integer price;
    private final LocalDateTime registeredAt;
    private final Integer stockQuantity;

    public RegisteredItemDto(Item item){
        this(
          item.getId(),
          item.getName(),
          item.getPrice(),
          item.getRegisterDate(),
          item.getStockQuantity()
        );
    }

    public ItemInfoResponse toResponse(){
        return new ItemInfoResponse(itemId,name,price,registeredAt,stockQuantity);
    }
}
