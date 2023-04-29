package toy.paymentapi.order.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.paymentapi.order.domain.Item;
import toy.paymentapi.order.repository.ItemRepository;
import toy.paymentapi.order.service.dto.ItemPageableDto;
import toy.paymentapi.support.error.ErrorCode;
import toy.paymentapi.support.error.PaymentApiException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

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

    }

    @AfterEach
    void clear(){
        itemRepository.deleteAll();
    }



    @DisplayName("상품 페이징 테스트 - 정상처리")
    @Test
    void pagedItemTestSuccess(){
        //given
        saveItem();
        Integer pageNum = 0;
        Integer itemSize = 10;

        //when
        ItemPageableDto itemPageableDto = itemService.pagedItemSortByRegisteredAt(pageNum, itemSize);

        //then
        assertThat(itemPageableDto.getLastPageNum()).isEqualTo(2);
        assertThat(itemPageableDto.getTotalItems()).isEqualTo(25);
        assertThat(itemPageableDto.getCurrentPageNum()).isEqualTo(0);
        assertThat(itemPageableDto.getItems().size()).isEqualTo(10);
    }


    @DisplayName("상품 페이징 테스트 - 등록된 상품이 없을시 예외 발생")
    @Test
    void pagedItemTestFail(){
        //given
        Integer pageNum = 0;
        Integer itemSize = 10;

        //when
        //then
        Assertions.assertThatThrownBy(()-> itemService.pagedItemSortByRegisteredAt(pageNum, itemSize))
                .isInstanceOf(PaymentApiException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.NO_CONTENT_ITEM);
    }

    @DisplayName("상품 페이징 테스트 - 없는 페이지 요청시 예외 발생")
    @Test
    void pagedItemTestFail2(){
        //given
        saveItem();
        Integer pageNum = 4;
        Integer itemSize = 10;

        //when
        //then
        Assertions.assertThatThrownBy(()-> itemService.pagedItemSortByRegisteredAt(pageNum, itemSize))
                .isInstanceOf(PaymentApiException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.NOT_FOUND_PAGE_NUM);
    }

    private void saveItem() {
        for(int i=1; i<=25; i++){
            Item item = Item.createItem("상품_" + i, i * 100, LocalDateTime.now(), 10);
            itemRepository.save(item);
        }
    }
}