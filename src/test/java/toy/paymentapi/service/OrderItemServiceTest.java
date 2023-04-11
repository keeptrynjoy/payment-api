package toy.paymentapi.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toy.paymentapi.domain.Item;
import toy.paymentapi.domain.OrderItem;
import toy.paymentapi.repository.ItemRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class OrderItemServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;


    @Test
    void convertItemsIntoOrderItems() {
        //given

        Item item1 = new Item("사과", 1000, LocalDateTime.now(), 10);
        Item item2 = new Item("바나나", 1000, LocalDateTime.now(), 10);
        Item item3 = new Item("딸기", 1000, LocalDateTime.now(), 10);
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
        OrderItemDto oItem1 = new OrderItemDto(1L,10);
        OrderItemDto oItem2 = new OrderItemDto(2L,10);
        OrderItemDto oItem3 = new OrderItemDto(3L,10);

        //when
        List<OrderItem> orderItems = convertItems(oItem1, oItem2, oItem3);

        //then
        Assertions.assertThat(orderItems.get(0).getItem().getId()).isEqualTo(item1.getId());
        Assertions.assertThat(orderItems.get(1).getItem().getId()).isEqualTo(item2.getId());
        Assertions.assertThat(orderItems.get(2).getItem().getId()).isEqualTo(item3.getId());
    }

    private List<OrderItem> convertItems(OrderItemDto... orderItemDtos) {

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDto or : orderItemDtos) {
            Item item = em.find(Item.class, or.getId());
            OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), or.getCount());
            orderItems.add(orderItem);
        }

        return orderItems;

    }


}

@Getter
@NoArgsConstructor
@AllArgsConstructor
class OrderItemDto{
    Long id;
    int count;
}