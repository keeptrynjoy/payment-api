package toy.paymentapi.order.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.paymentapi.order.domain.Item;
import toy.paymentapi.order.domain.OrderItem;
import toy.paymentapi.order.repository.ItemRepository;
import toy.paymentapi.order.repository.OrderRepository;
import toy.paymentapi.order.service.dto.OrderItemDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
class OrderItemServiceTest {

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ItemRepository itemRepository;


    @Test
    void createOrderItem(){
        //given
        Item item = Item.createItem("사과", 10000, LocalDateTime.now(), 10);
        Item saveItem = itemRepository.save(item);

        OrderItemDto orderItemDto = new OrderItemDto(saveItem.getId(),10);

        //when
        OrderItem orderItem = orderItemService.createOrderItem(orderItemDto);

        //then
        Assertions.assertThat(orderItem.getItem()).isEqualTo(saveItem);

    }
}