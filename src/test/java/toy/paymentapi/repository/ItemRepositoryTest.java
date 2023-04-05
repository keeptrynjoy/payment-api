package toy.paymentapi.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.paymentapi.domain.Item;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static toy.paymentapi.domain.QItem.item;

@Slf4j
@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemQueryRepository itemQueryRepository;

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

    @Test
    void 재고수량검증(){
        //given
        Item item = getItem();
        Item saveItem = itemRepository.save(item);

        //when&then
        Item findItem = itemQueryRepository.checkStockQuantity(saveItem.getId(), 9);
        assertThat(findItem).isEqualTo(saveItem);
    }

    private static Item getItem() {
        return new Item("사과", 1000, LocalDateTime.now(), 10);
    }
}