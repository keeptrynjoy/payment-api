package toy.paymentapi.support.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentApiException extends RuntimeException{

    private final ErrorCode errorCode;

}
