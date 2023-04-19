package toy.paymentapi.order.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import toy.paymentapi.order.domain.CouponIssue;
import toy.paymentapi.order.domain.Item;
import toy.paymentapi.order.domain.QItem;

import javax.persistence.EntityManager;

import static toy.paymentapi.order.domain.QCouponIssue.couponIssue;
import static toy.paymentapi.order.domain.QItem.*;
import static toy.paymentapi.order.domain.QPoint.point;
import static toy.paymentapi.payment.domain.QMember.member;


@Repository
public class OrderQueryRepository {

    private final JPAQueryFactory query;

    public OrderQueryRepository(EntityManager em){
        this.query = new JPAQueryFactory(em);
    }
    //== Item ==//
    /**
    * 재고 검증(주문 요청한 수량 보다 보유재고가 많을 경우만 조회)
    **/
    public Item checkStockQuantity(Long itemId, int stockQuantity)  {
        return query
                .select(item)
                .from(item)
                .where(
                        item.id.eq(itemId),
                        item.stockQuantity.gt(stockQuantity)
                ).fetchOne();
    }

    //== Point ==//
    /**
    * 회원 포인트 조회(내역상 제일 최근일자의 포인트)
    **/
    public Integer findPointByMember(Long memberId){
        return query.select(point.beforePoint)
                .from(point)
                .orderBy(point.writeDate.asc())
                .where(point.member.id.eq(memberId))
                .fetchFirst();
    }

    //== CouponIssue ==//
    /**
    * memberId로 발급된 쿠폰 조회
    **/
    public CouponIssue findCouponIssuedByMember(Long memberId, Long couponIssueId){
        return query
                .selectFrom(couponIssue)
                .join(couponIssue.member, member)
                .where(
                        member.id.eq(memberId)
                , couponIssue.id.eq(couponIssueId))
                .fetchOne();
    }
}
