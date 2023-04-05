package toy.paymentapi.support.error;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaymentApiException extends RuntimeException{

    private final ErrorCode errorCode;
}
