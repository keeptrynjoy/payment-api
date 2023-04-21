package toy.paymentapi.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.paymentapi.order.domain.CouponIssue;
import toy.paymentapi.order.domain.Order;
import toy.paymentapi.order.domain.OrderItem;
import toy.paymentapi.order.repository.MemberRepository;
import toy.paymentapi.order.repository.OrderQueryRepository;
import toy.paymentapi.order.repository.OrderRepository;
import toy.paymentapi.order.service.dto.OrderItemDto;
import toy.paymentapi.order.service.dto.RegisteredOrderDto;
import toy.paymentapi.order.domain.Member;
import toy.paymentapi.support.error.ErrorCode;
import toy.paymentapi.support.error.PaymentApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static toy.paymentapi.support.error.ErrorCode.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderItemService orderItemService;
    private final OrderQueryRepository orderQueryRepository;
    private final CouponService couponService;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;


    @Transactional
    public RegisteredOrderDto registerOrder(Long memberId, Integer usePoint, Long useCoupon, List<OrderItemDto> orderItems){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(NoSuchElementException::new);

        //재고 확인 및 주문 상품 생성
        List<OrderItem> createdOrderItems = new ArrayList<>();

        for(OrderItemDto orderItemDto :orderItems){
            OrderItem orderItem = orderItemService.createOrderItem(orderItemDto);
            createdOrderItems.add(orderItem);
        }


        //보유 포인트 검증
        Integer findPoint = orderQueryRepository.findPointByMember(member.getId());
        if(findPoint < usePoint){
            throw ErrorCode.throwNotEnoughPoint();
        }

        //쿠폰 발급 내역 조회
        CouponIssue findCoupon = couponService.findCouponById(useCoupon);

        //주문 생성
        Order order = Order.createOrder(member, usePoint, findCoupon.getDiscountAmount(), createdOrderItems);
        Order newOrder = orderRepository.save(order);

        return new RegisteredOrderDto(newOrder);
    }


    @Transactional(readOnly = true)
    public boolean confirmOrder(Long orderId, Integer confirmPrice){

        Order order = orderRepository.findById(orderId)
                .orElseThrow(ErrorCode::throwNotFoundOrder);

        // 결제 금액 검증
        if(confirmPrice != order.getTotalAmount()){
            throw ErrorCode.throwNotMatchAmounts();
        }

        List<OrderItem> orderItems = order.getOrderItems();

        for (OrderItem oi: orderItems){
            log.info("[{}] 상품 주문 단가 :{}, 수량 : {}, 총 금액 :{} ",oi.getItem().getName(), oi.getOrderPrice(), oi.getCount(), oi.getTotalPrice());

            int orderItemCount = oi.getCount();
            int recentlyStock = oi.getStockQuantity();

            if(recentlyStock == 0){
                throw ErrorCode.throwStockEmpty();
            } else if(recentlyStock < orderItemCount){
                throw ErrorCode.throwNotEnoughStockQuantity();
            }

        }

        log.info("주문 총 금액 : {}",order.getTotalAmount());
        return true;
    }
}
