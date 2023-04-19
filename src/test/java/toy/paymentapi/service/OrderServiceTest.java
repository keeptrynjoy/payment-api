package toy.paymentapi.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.paymentapi.order.domain.*;
import toy.paymentapi.order.repository.*;
import toy.paymentapi.order.service.CouponService;
import toy.paymentapi.order.service.OrderItemService;
import toy.paymentapi.order.service.OrderService;
import toy.paymentapi.payment.domain.Member;
import toy.paymentapi.order.service.dto.OrderItemDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
@Transactional
class OrderServiceTest {

    Member member;
    Point point;
    CouponIssue couponIssue;
    Coupon coupon;

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
    OrderQueryRepository orderQueryRepository;

    @BeforeEach
    void init(){
        member = Member.createMember("김성민", "01040289186", "ijkim246@naver.com");
        Member saveMember = memberRepository.save(member);
        Item item = Item.createItem("사과", 10000, LocalDateTime.now(), 10);
        itemRepository.save(item);
        Point pointBySignUp = Point.createPointBySignUp(saveMember);
        pointRepository.save(pointBySignUp);
        Coupon itemCoupon = Coupon.createItemCoupon(CouponType.ITEMORDER, 1000, 2L);
        couponRepository.save(itemCoupon);
        CouponIssue couponIssue = CouponIssue.couponIssuedToMember(LocalDateTime.of(2023, 12, 31, 12, 0, 0, 0), saveMember, itemCoupon);

    }


    @DisplayName("Order Service Test - order register")
    @Test
    void registerOrderSuccess(){
        //given
        Member findMember = memberRepository.findById(member.getId()).get();

        //when
        orderService.registerOrder(findMember,100,couponIssue,)

        //then

    }



}