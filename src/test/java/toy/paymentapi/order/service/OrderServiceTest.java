package toy.paymentapi.order.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.paymentapi.order.domain.*;
import toy.paymentapi.order.domain.Order;
import toy.paymentapi.order.repository.*;
import toy.paymentapi.order.service.OrderService;
import toy.paymentapi.order.service.dto.RegisteredOrderDto;
import toy.paymentapi.order.domain.Member;
import toy.paymentapi.order.service.dto.OrderItemDto;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
@Transactional
class OrderServiceTest {

    Member member;
    Point point;
    CouponIssue couponIssue;
    Coupon coupon;
    Item item1;
    Item item2;

    @Autowired
    OrderService orderService;

    @Autowired
    MemberRepository  memberRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    PointRepository pointRepository;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    CouponIssueRepository couponIssueRepository;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void init(){
        member = Member.createMember("김성민", "01040289186", "ijkim246@naver.com");
        Member saveMember = memberRepository.save(member);

        item1 = Item.createItem("사과", 1000, LocalDateTime.now(), 18);
        Item saveItem1 = itemRepository.save(item1);
        item2 = Item.createItem("바나나", 2000, LocalDateTime.now(), 20);
        Item saveItem2 = itemRepository.save(item2);

        point= Point.createPointBySignUp(saveMember);
        pointRepository.save(point);

        coupon= Coupon.createItemCoupon(CouponType.ITEMORDER, 1000, saveItem1.getId());
        Coupon saveCoupon = couponRepository.save(coupon);

        couponIssue = CouponIssue.couponIssuedToMember(LocalDateTime.now().plusMonths(3L), saveMember, saveCoupon);
        couponIssueRepository.save(couponIssue);

    }

    @AfterEach
    void delete(){
        couponIssueRepository.deleteAll();
        couponRepository.deleteAll();
        pointRepository.deleteAll();
        itemRepository.deleteAll();
        memberRepository.deleteAll();
    }


    @DisplayName("결제전 주문 등록 - 정상처리 테스트")
    @Test
    void registerOrderSuccess(){
        //given
        Member findMember = memberRepository.findById(member.getId()).get();
        CouponIssue findCouponIssue = couponIssueRepository.findById(couponIssue.getId()).get();

        OrderItemDto orderItemDto = new OrderItemDto(item1.getId(),10);

        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        orderItemDtos.add(orderItemDto);

        //when
        RegisteredOrderDto registeredOrderDto = orderService.registerOrder(findMember.getId(), 100, findCouponIssue.getId(), orderItemDtos);

        //then
        Order order = orderRepository.findById(registeredOrderDto.getId()).get();
        Assertions.assertThat(10000).isEqualTo(order.getTotalAmount());
    }

    @DisplayName("아임포트 결제전 confirm process - 정상처리 테스트")
    @Test
    void confirmOrderSuccess(){
        //given
        Member findMember = memberRepository.findById(member.getId()).get();
        CouponIssue findCouponIssue = couponIssueRepository.findById(couponIssue.getId()).get();

        OrderItemDto orderItemDto1 = new OrderItemDto(item1.getId(),1);
        OrderItemDto orderItemDto2 = new OrderItemDto(item2.getId(),1);
        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        orderItemDtos.add(orderItemDto1);
        orderItemDtos.add(orderItemDto2);
        RegisteredOrderDto registeredOrderDto = orderService.registerOrder(findMember.getId(), 100, findCouponIssue.getId(), orderItemDtos);

        em.flush();

        //when
        boolean result = orderService.confirmOrder(registeredOrderDto.getId(), 3000);

        //then
        Assertions.assertThat(result).isTrue();

    }
}