package toy.paymentapi.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toy.paymentapi.order.service.OrderService;
import toy.paymentapi.order.controller.request.OrderResisterRequest;
import toy.paymentapi.order.controller.response.OrderRegisteredResponse;
import toy.paymentapi.order.service.dto.RegisteredOrderDto;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping("register")
    public ResponseEntity<OrderRegisteredResponse> registerOrder(OrderResisterRequest request){

        RegisteredOrderDto registeredOrderDto = orderService.registerOrder(
                request.getMemberId(), request.getUsePoint(), request.getUseCoupon(), request.getOrderItems());

        return ResponseEntity.ok(registeredOrderDto.toResponse());
    }

}
