package toy.paymentapi.order.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.paymentapi.order.domain.Item;
import toy.paymentapi.order.repository.ItemRepository;
import toy.paymentapi.order.service.dto.ItemPageableDto;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    void init(){
        for(int i=1; i<=25; i++){
            Item item = Item.createItem("상품_" + i, i * 100, LocalDateTime.now(), 10);
            itemRepository.save(item);
        }

    }

    @DisplayName("상품 조회 및 페이징 테스트 - 정상처리")
    @Test
    void pagedItemSortByRegisteredAtTest(){
        //given
        Integer pageNum = 0;
        Integer itemSize = 10;

        //when
        ItemPageableDto itemPageableDto = itemService.pagedItemSortByRegisteredAt(pageNum, itemSize);

        //then
        Assertions.assertThat(itemPageableDto.getLastPageNum()).isEqualTo(2);
        Assertions.assertThat(itemPageableDto.getTotalItems()).isEqualTo(25);
        Assertions.assertThat(itemPageableDto.getCurrentPageNum()).isEqualTo(0);
        Assertions.assertThat(itemPageableDto.getItems().size()).isEqualTo(10);
    }
}