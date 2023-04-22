package toy.paymentapi.payment.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PortOneAccessDto {
    private String imp_key;
    private String imp_secret;
    private String access_token;

    public PortOneAccessDto(String key, String secret) {
        this.imp_key = key;
        this.imp_secret = secret;
    }
}
