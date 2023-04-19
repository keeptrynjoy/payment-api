package toy.paymentapi.order.service;

import lombok.RequiredArgsConstructor;
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
import toy.paymentapi.payment.domain.Member;
import toy.paymentapi.support.error.PaymentApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static toy.paymentapi.support.error.ErrorCode.*;


@Service
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


        //재고 확인 및 주문 생성
        List<OrderItem> createdOrderItems = new ArrayList<>();
        while (!orderItems.isEmpty()){
            createdOrderItems = orderItems.stream()
                    .map(o -> orderItemService.createOrderItem(o))
                    .collect(Collectors.toList());
        }

        //보유 포인트 검증
        Integer findPoint = orderQueryRepository.findPointByMember(member.getId());
        if(findPoint < usePoint){
            throw new PaymentApiException(NOT_ENOUGH_POINT);
        }

        //쿠폰 조회
        CouponIssue findCoupon = couponService.findCouponById(useCoupon);


        //주문 생성
        Order order = Order.createOrder(member, usePoint, findCoupon.getDiscountAmount(), createdOrderItems);
        Order newOrder = orderRepository.save(order);

        return new RegisteredOrderDto(newOrder);
    }
}
