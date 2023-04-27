package toy.paymentapi.order.controller.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.paymentapi.order.service.dto.ItemDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemRegisterRequest {
    @NotNull
    private String name;
    @NotNull
    private Integer price;
    @Min(0)
    private Integer quantity;

    public ItemDto toItemRegisterDto(){
        return ItemDto.builder()
                .name(this.name)
                .price(this.price)
                .stockQuantity(this.quantity)
                .build();

    }
}
