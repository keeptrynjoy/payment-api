package toy.paymentapi.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import toy.paymentapi.domain.Item;
import toy.paymentapi.domain.Member;
import toy.paymentapi.domain.QPoint;

import javax.persistence.EntityManager;

import static toy.paymentapi.domain.QItem.item;
import static toy.paymentapi.domain.QPoint.*;

@Repository
public class PointQueryRepository {

    private final JPAQueryFactory query;

    public PointQueryRepository(EntityManager em){
        this.query = new JPAQueryFactory(em);
    }

    public Integer findPointByMember(Long memberId){
        return query.select(point.beforePoint)
                .from(point)
                .orderBy(point.writeDate.asc())
                .where(point.member.id.eq(memberId))
                .fetchFirst();
    }

}
