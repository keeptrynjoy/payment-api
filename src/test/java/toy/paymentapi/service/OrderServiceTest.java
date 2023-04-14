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
import java.util.List;

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

        //when


        //then

    }

    private void order(Long memberId, CouponIssue couponIssue, int usePoint, List<OrderItemDto> orderItemDtoList){

        Member member = memberRepository.findById(memberId).get();
        Integer afterPoint = queryRepository.findPointByMember(member.getId());

        List<OrderItem> orderItems = orderItemService.createOrderItems(orderItemDtoList);

        Order resOrder = Order.createOrder(member, couponIssue, usePoint, orderItems);

        Point pointByOrder = Point.createPointByOrder(afterPoint, usePoint, resOrder.getId(), member);

        pointRepository.save(pointByOrder);

    }


}