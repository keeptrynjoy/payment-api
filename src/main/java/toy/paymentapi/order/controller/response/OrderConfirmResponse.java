package toy.paymentapi.order.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class OrderConfirmResponse {
    ResponseEntity<String> responseEntity;
}
