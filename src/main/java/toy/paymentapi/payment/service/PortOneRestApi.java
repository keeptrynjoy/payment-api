package toy.paymentapi.payment.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PortOneRestApi {
    HOST("https://api.iamport.kr"),
    AUTHENTICATE("/users/getToken");

    private final String url;
}
