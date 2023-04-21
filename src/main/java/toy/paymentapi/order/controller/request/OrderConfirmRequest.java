package toy.paymentapi.order.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderConfirmRequest {
    private Long merchant_uid;
    private int amount;
}
