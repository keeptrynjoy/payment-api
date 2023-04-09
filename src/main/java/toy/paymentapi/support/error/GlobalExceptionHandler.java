package toy.paymentapi.support.error;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
public class GlobalExceptionHandler{

    @ExceptionHandler(value = PaymentApiException.class)
    protected ResponseEntity<ErrorResponse> PaymentApiException(PaymentApiException ex) {
        log.error("PaymentApiEException :: ", ex);

        ErrorCode error_code = ex.getErrorCode();
        return ResponseEntity.status(error_code.getHttpStatus()).body(ErrorResponse.toErrorResponse(error_code));
    }
}
