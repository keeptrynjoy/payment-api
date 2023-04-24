package toy.paymentapi.payment.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PortOnePaymentDto {

    private String imp_uid;
    private String merchant_uid;
    private String pay_method;
    private String pg_provider;
    private Long amount;
    private String buyer_name;
    private String buyer_email;
    private String buyer_tel;
    private String cancel_history;
}
