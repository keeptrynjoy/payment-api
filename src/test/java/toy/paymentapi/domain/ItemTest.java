package toy.paymentapi.domain;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.paymentapi.order.domain.Item;
import toy.paymentapi.order.repository.ItemRepository;
import toy.paymentapi.support.error.ErrorCode;
import toy.paymentapi.support.error.PaymentApiException;

import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
@Transactional
class ItemTest {

    @Autowired
    private ItemRepository itemRepository;

    @DisplayName("재고 차감 - 정상 확인")
    @Test
    void removeStockSuccess(){
        //given
        Item item = new Item("사과", 10000, LocalDateTime.now(), 10);

        //when
        item.removeStock(5);
        Item saveItem = itemRepository.save(item);

        //then
        Assertions.assertThat(saveItem.getStockQuantity()).isEqualTo(5);
    }

    @DisplayName("재고 차감 - 예외 발생")
    @Test
    void removeStockException(){
        //given
        Item item = new Item("사과", 10000, LocalDateTime.now(), 1);

        //when&then
        Assertions.assertThatThrownBy(()->item.removeStock(5))
                .isInstanceOf(PaymentApiException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.NOT_ENOUGH_STOCK_QUANTITY);
    }
}