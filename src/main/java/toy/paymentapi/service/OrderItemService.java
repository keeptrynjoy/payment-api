package toy.paymentapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.paymentapi.domain.Item;
import toy.paymentapi.domain.OrderItem;
import toy.paymentapi.repository.ItemRepository;
import toy.paymentapi.service.dto.OrderItemDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final ItemRepository itemRepository;

    public List<OrderItem> createOrderItems(List<OrderItemDto> orderItemDto) {

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDto or : orderItemDto) {
            Item item = itemRepository.getReferenceById(or.getId());
            OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), or.getCount());
            orderItems.add(orderItem);
        }

        return orderItems;

    }
}
