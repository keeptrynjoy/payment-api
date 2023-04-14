package toy.paymentapi.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
class CouponRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    CouponIssueRepository couponIssueRepository;

    @Autowired
    QueryRepository queryRepository;

    @PersistenceContext
    EntityManager em;

    @DisplayName("회원ID와 쿠폰발급ID로 쿠폰 발급 조회")
    @Test
    @Transactional
    void findCouponIssueByMember(){
        //given
        Member createMember = Member.createMember("김성민", "01040289186", "ijkim246@naver.com");
        Member member = memberRepository.save(createMember);

        Item item = Item.createItem("사과", 10000, LocalDateTime.now(), 10);
        Item saveItem = itemRepository.save(item);

        Coupon itemCoupon1 = Coupon.createItemCoupon(CouponType.ITEMORDER, 1000, saveItem.getId());
        Coupon itemCoupon2 = Coupon.createItemCoupon(CouponType.ITEMORDER, 2000, saveItem.getId());
        Coupon saveItemCoupon = couponRepository.save(itemCoupon1);
        Coupon saveItemCoupon2 = couponRepository.save(itemCoupon2);

        CouponIssue couponIssued = CouponIssue.couponIssuedToMember(LocalDateTime.now(), member, saveItemCoupon);
        CouponIssue couponIssued2 = CouponIssue.couponIssuedToMember(LocalDateTime.now(), member, saveItemCoupon2);
        CouponIssue saveCouponIssued = couponIssueRepository.save(couponIssued);
        CouponIssue saveCouponIssued2 = couponIssueRepository.save(couponIssued2);

        em.flush();

        //when
        log.info("조회 시작");
        CouponIssue couponIssuedByMember = queryRepository.findCouponIssuedByMember(member.getId(),couponIssued2.getId());
        log.info("조회 끝");

        //then
        log.info(couponIssuedByMember.toString());
        Assertions.assertThat(couponIssuedByMember).isEqualTo(saveCouponIssued2);
    }



    @AfterEach
    private void clean(){
        couponIssueRepository.deleteAll();
        couponRepository.deleteAll();
        itemRepository.deleteAll();
        memberRepository.deleteAll();
    }
}