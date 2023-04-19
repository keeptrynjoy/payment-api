package toy.paymentapi.support.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentApiException extends RuntimeException{

    private final ErrorCode errorCode;
}
