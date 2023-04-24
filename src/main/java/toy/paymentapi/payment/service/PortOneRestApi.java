package toy.paymentapi.payment.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PortOneRestApi {
    HOST("https://api.iamport.kr"),
    AUTHENTICATE("/users/getToken"),
    PAYMENTS_IMPUID("/payments"),

    ERROR_TEST("");


    private final String uri;

}
