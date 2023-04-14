package toy.paymentapi.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.paymentapi.domain.Item;
import toy.paymentapi.support.error.ErrorCode;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private QueryRepository queryRepository;

    @DisplayName("상품 등록")
    @Test
    void save(){
        //given
        Item item = getItem();

        //when
        Item saveItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId()).get();
        assertThat(findItem).isEqualTo(saveItem);
    }

    @DisplayName("재고 수량 검증 - 정상")
    @Test
    void findStockSuccess(){
        //given
        Item item = getItem();
        Item saveItem = itemRepository.save(item);

        //when&then
        Item findItem = queryRepository.checkStockQuantity(saveItem.getId(), 9);
        assertThat(findItem).isEqualTo(saveItem);
    }

    @DisplayName("재고 수량 검증 - 재고 부족")
    @Test
    void findStockException(){
        //given
        Item item = getItem();
        Item saveItem = itemRepository.save(item);

        //when
        Item findItem = queryRepository.checkStockQuantity(saveItem.getId(), 20);

        //&then
        assertThatThrownBy(()->findItem.getId())
                .isInstanceOf(RuntimeException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.NOT_ENOUGH_STOCK_QUANTITY);
    }


    private static Item getItem() {
        return new Item("사과", 1000, LocalDateTime.now(), 10);
    }
}