package toy.paymentapi.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.paymentapi.domain.Coupon;
import toy.paymentapi.domain.CouponIssue;
import toy.paymentapi.domain.Enum.CouponType;
import toy.paymentapi.domain.Item;
import toy.paymentapi.domain.Member;
import toy.paymentapi.repository.CouponIssueRepository;
import toy.paymentapi.repository.CouponRepository;
import toy.paymentapi.repository.ItemRepository;
import toy.paymentapi.repository.MemberRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CouponServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CouponService couponService;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CouponIssueRepository couponIssueRepository;

    @Autowired
    EntityManager em;

    @DisplayName("발급된 쿠폰 사용한 상태로 변경시 정상 체크")
    @Test
    @Transactional
    void changeToUsedCouponTest(){
        //given
        Member createMember = Member.createMember("김성민", "01040289186", "ijkim246@naver.com");
        Member member = memberRepository.save(createMember);

        Item item = Item.createItem("사과", 10000, LocalDateTime.now(), 10);
        Item saveItem = itemRepository.save(item);

        Coupon itemCoupon1 = Coupon.createItemCoupon(CouponType.ITEMORDER, 1000, saveItem.getId());
        Coupon saveItemCoupon = couponRepository.save(itemCoupon1);

        CouponIssue couponIssued = CouponIssue.couponIssuedToMember(LocalDateTime.now(), member, saveItemCoupon);
        CouponIssue saveCouponIssued = couponIssueRepository.save(couponIssued);
        em.flush();

        //when
        boolean result = couponService.changeToUsedCoupon(member.getId(), saveCouponIssued.getId());

        //then
        Assertions.assertThat(result).isTrue();
    }
}