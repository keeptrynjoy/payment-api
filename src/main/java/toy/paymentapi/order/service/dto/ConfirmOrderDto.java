package toy.paymentapi.order.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ConfirmOrderDto {

    private String merchant_uid;
}
