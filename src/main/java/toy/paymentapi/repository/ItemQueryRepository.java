package toy.paymentapi.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import toy.paymentapi.domain.Item;
import toy.paymentapi.domain.QItem;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static toy.paymentapi.domain.QItem.*;

@Repository
public class ItemQueryRepository{

    private final JPAQueryFactory query;

    public ItemQueryRepository(EntityManager em){
        this.query = new JPAQueryFactory(em);
    }

    public Item checkStockQuantity(Long itemId, int stockQuantity){
        return query
                .select(item)
                .from(item)
                .where(
                        item.id.eq(itemId),
                        item.stockQuantity.gt(stockQuantity)
                ).fetchOne();
    }

}
