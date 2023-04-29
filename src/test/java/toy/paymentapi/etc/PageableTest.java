package toy.paymentapi.etc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import toy.paymentapi.order.domain.Item;
import toy.paymentapi.order.repository.ItemRepository;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
@Transactional
public class PageableTest {


    @Autowired
    ItemRepository itemRepository;


    @BeforeEach
    void init(){
        for(int i=1; i<=25; i++){
            Item item = Item.createItem("상품_" + i, i * 100, LocalDateTime.now(), 10);
            itemRepository.save(item);
        }

    }

    @Test
    void pageableTest(){
        //given
        Pageable pageRequest = PageRequest.of(1,10);

        //when
        Page<Item> all = itemRepository.findAll(pageRequest);

        Pageable pageable = all.getPageable();

        //then
        log.info("first : {} ",pageable.first());
        log.info("next : {} ",pageable.next());
        log.info("withPage : {} ",pageable.withPage(2));
        log.info("isPaged : {} ",pageable.isPaged());
        log.info("hasPrevious : {} ",pageable.hasPrevious());

    }

    @Test
    void sliceTest(){
        //given
        Pageable pageRequest = Pageable.ofSize(10);

        //when
        Page<Item> resultItems = itemRepository.findAll(pageRequest);

        //then
        log.info("getTotalPages : {} ",resultItems.getTotalPages());
        log.info("getTotalElements : {} ",resultItems.getTotalElements());
        log.info("getNumber : {} ",resultItems.getNumber());
        log.info("getNumberOfElements : {} ",resultItems.getNumberOfElements());
        log.info("getPageable : {} ",resultItems.getPageable());
        log.info("getSize : {} ",resultItems.getSize());
        log.info("getSort : {} ",resultItems.getSort());
        log.info("getHasContent : {} ",resultItems.hasContent());
        log.info("hasNext : {} ",resultItems.hasNext());
        log.info("hasPrevious : {} ",resultItems.hasPrevious());
        log.info("isFirst : {} ",resultItems.isFirst());
        log.info("isLast : {} ",resultItems.isLast());
        log.info("nextOrLastPageable : {} ",resultItems.nextOrLastPageable());
        log.info("nextPageable : {} ",resultItems.nextPageable());
        log.info("previousOrFirstPageable : {} ",resultItems.previousOrFirstPageable());
        log.info("previousPageable : {} ",resultItems.previousPageable());
    }
}
