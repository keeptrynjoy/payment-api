package toy.paymentapi.order.controller.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import toy.paymentapi.order.service.dto.OrderItemDto;
import toy.paymentapi.support.validator.constrain.NotEmptyCollection;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResisterRequest {
    @NotEmpty(message = "")
    private Long memberId;
    private Integer usePoint;
    private Long useCoupon;
    @NotEmptyCollection
    private List<OrderItemDto> orderItems;
}
