package toy.paymentapi.order.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.paymentapi.order.repository.CouponIssueRepository;
import toy.paymentapi.order.repository.CouponRepository;
import toy.paymentapi.order.repository.ItemRepository;
import toy.paymentapi.order.repository.MemberRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
class CouponIssueTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    CouponIssueRepository couponIssueRepository;

    @Autowired
    ItemRepository itemRepository;

    @DisplayName("CouponIssue Test - Issued Success")
    @Test
    void couponIssuedToMember(){
        //given
        Member member = Member.createMember("김성민", "01040289186", "ijkim246@naver.com");
        Member saveMember = memberRepository.save(member);

        Item item = Item.createItem("사과", 10000, LocalDateTime.now(), 10);
        Item saveItem = itemRepository.save(item);

        Coupon itemCoupon = Coupon.createItemCoupon(CouponType.ITEMORDER, 1000, saveItem.getId());
        Coupon saveCoupon = couponRepository.save(itemCoupon);


        //when
        CouponIssue couponIssue = CouponIssue.couponIssuedToMember(LocalDateTime.now().plusMonths(4L), saveMember, saveCoupon);
        CouponIssue saveCouponIssued = couponIssueRepository.save(couponIssue);
        log.info(saveCouponIssued.toString());

        //then
        assertThat(saveCouponIssued.getCoupon().getId()).isEqualTo(saveCoupon.getId());
    }
}