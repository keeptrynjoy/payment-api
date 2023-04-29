package toy.paymentapi.support.error;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //== 400, valid 검증 예외 발생 ==//
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("MethodArgumentNotValidException :: ", ex);

        List<String> errors =generateErrors(ex);
        ErrorResponse response = new ErrorResponse(BAD_REQUEST, ex.getLocalizedMessage(), errors);

        return ResponseEntity.status(response.getStatus()).headers(headers).body(response);
    }

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

    private List<String> generateErrors(BindException ex) {
        List<String> errors = new ArrayList<>();
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        for (ObjectError error : allErrors) {
            errors.add(error.getDefaultMessage());
        }
        return errors;
    }
}
