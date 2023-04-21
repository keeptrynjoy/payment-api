package toy.paymentapi.support.error;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("NoHandlerFoundException :: ", ex);

        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        ErrorResponse response = new ErrorResponse(NOT_FOUND, ex.getLocalizedMessage(), error);
        return ResponseEntity.status(response.getStatus()).headers(headers).body(response);
    }


    @ExceptionHandler(value = PaymentApiException.class)
    protected ResponseEntity<ErrorResponse> PaymentApiException(PaymentApiException ex) {
        log.error("PaymentApiException :: ", ex);

        ErrorCode error_code = ex.getErrorCode();
        return ResponseEntity.status(error_code.getHttpStatus()).body(ErrorResponse.toErrorResponse(error_code));
    }
}
