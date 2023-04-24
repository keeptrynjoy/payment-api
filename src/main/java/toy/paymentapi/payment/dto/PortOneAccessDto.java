package toy.paymentapi.payment.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PortOneAccessDto {
    private String imp_key;
    private String imp_secret;
    private String access_token;

    public PortOneAccessDto(String key, String secret) {
        this.imp_key = key;
        this.imp_secret = secret;
    }
}
