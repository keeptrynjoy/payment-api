package toy.paymentapi.order.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ItemInfoResponse {
    private Long itemId;
    private String name;
    private Integer price;
    private LocalDateTime registeredAt;
    private Integer stockQuantity;
}
