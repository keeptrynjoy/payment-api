package toy.paymentapi.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import toy.paymentapi.domain.*;

import javax.persistence.EntityManager;

import static toy.paymentapi.domain.QCouponIssue.*;
import static toy.paymentapi.domain.QItem.*;
import static toy.paymentapi.domain.QMember.*;
import static toy.paymentapi.domain.QPoint.point;

@Repository
public class QueryRepository {

    private final JPAQueryFactory query;

    public QueryRepository(EntityManager em){
        this.query = new JPAQueryFactory(em);
    }
    //== Item ==//
    /**
    * 재고 검증(주문 요청한 수량 보다 보유재고가 많을 경우만 조회)
    **/
    public Item checkStockQuantity(Long itemId, int stockQuantity){
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
    public CouponIssue findCouponIssuedByMember(Long memberId,Long couponIssueId){
        return query
                .selectFrom(couponIssue)
                .join(couponIssue.member, member)
                .where(
                        member.id.eq(memberId)
                , couponIssue.id.eq(couponIssueId))
                .fetchOne();
    }
}
