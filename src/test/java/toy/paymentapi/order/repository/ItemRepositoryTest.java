package toy.paymentapi.order.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.paymentapi.order.domain.Item;
import toy.paymentapi.order.repository.ItemRepository;
import toy.paymentapi.order.repository.OrderQueryRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderQueryRepository orderQueryRepository;

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

    @DisplayName("재고 수량 검증")
    @Test
    void findStockSuccess(){
        //given
        Item item = getItem();
        Item saveItem = itemRepository.save(item);

        //when&then
        Item findItem = orderQueryRepository.checkStockQuantity(saveItem.getId(), 9);
        assertThat(findItem).isEqualTo(saveItem);
    }


    private static Item getItem() {
        return new Item("사과", 1000, LocalDateTime.now(), 10);
    }
}