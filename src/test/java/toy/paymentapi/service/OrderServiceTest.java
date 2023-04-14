package toy.paymentapi.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.paymentapi.domain.*;
import toy.paymentapi.repository.*;
import toy.paymentapi.service.dto.OrderItemDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private CouponService couponService;

    @DisplayName("주문 저장")
    @Test
    void orderTest(){
        //given
        Member member = new Member("김성민","01040289186","sungmin4218@gmail.com");
        Member saveMember = memberRepository.save(member);
        Item item1 = new Item("사과", 1000, LocalDateTime.now(), 10);
        Item item2 = new Item("딸기", 1000, LocalDateTime.now(), 10);
        Item item3 = new Item("배", 1000, LocalDateTime.now(), 10);
        Item saveItem1 = itemRepository.save(item1);
        Item saveItem2 = itemRepository.save(item2);
        Item saveItem3 = itemRepository.save(item3);

        List<OrderItemDto> orderItemDtoList = new ArrayList<>();


        //when
        order(member.getId(),null,1000,orderItemDtoList);

        //then

    }

    private void order(Long memberId, CouponIssue couponIssue, int usePoint, List<OrderItemDto> orderItemDtoList){

        //1. 회원 조회
        Member member = memberRepository.findById(memberId).get();

        //2. 주문 생성
        List<OrderItem> orderItems = orderItemService.createOrderItems(orderItemDtoList);
        Order createOrder = Order.createOrder(member, couponIssue, usePoint, orderItems);

        //3. 기존 포인트 조회 및 사용한 포인트 내역 추가
        Integer afterPoint = queryRepository.findPointByMember(member.getId());
        Point pointByOrder = Point.createPointByOrder(afterPoint, usePoint, createOrder.getId(), member);
        pointRepository.save(pointByOrder);

        //4. 쿠폰 사용 여부 확인 및 사용 처리
        if(Optional.ofNullable(couponIssue).isPresent()){
            boolean changeToCouponStatus = couponService.changeToUsedCoupon(memberId, couponIssue.getId());

            if(changeToCouponStatus == false){
                throw new RuntimeException("쿠폰이 이미 사용한 상태입니다.");
            }
        }

        //5. 결제 요청(PG)


        //6. 결제 처리(DB)

    }


}