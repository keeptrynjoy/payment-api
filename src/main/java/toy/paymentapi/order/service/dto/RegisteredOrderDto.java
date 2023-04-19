package toy.paymentapi.order.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import toy.paymentapi.order.controller.response.OrderRegisteredResponse;
import toy.paymentapi.order.domain.Order;


@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisteredOrderDto {
    private final Long id;

    public RegisteredOrderDto(Order order) {
        this.id = order.getId();
    }

    public OrderRegisteredResponse toResponse() {
        return new OrderRegisteredResponse(id);
    }
}
