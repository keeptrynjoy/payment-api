package toy.paymentapi.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.paymentapi.order.domain.Item;
import toy.paymentapi.order.domain.OrderItem;
import toy.paymentapi.order.repository.ItemRepository;
import toy.paymentapi.order.service.dto.OrderItemDto;
import toy.paymentapi.support.error.PaymentApiException;

import java.util.NoSuchElementException;

import static toy.paymentapi.support.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final ItemRepository itemRepository;

    /**
     * 재고 검증 및 주문 상품 생성
     * OrderItemDto
     * - itemId : 상품 pk
     * - count : 상품의 주문량
     **/
    @Transactional
    public OrderItem createOrderItem(OrderItemDto orderItemDto){

       Item item = itemRepository.findById(orderItemDto.getItemId())
               .orElseThrow(NoSuchElementException::new);

       //주문량 대비 재고가 부족할 경우
       if(item.getStockQuantity() < orderItemDto.getCount()){
           throw new PaymentApiException(NOT_ENOUGH_STOCK_QUANTITY);

       //재고 없을 경우
       } else if (item.getStockQuantity() == 0){
           throw new PaymentApiException(STOCK_EMPTY);

       } else {
           return OrderItem.createOrderItem(item, item.getPrice(), orderItemDto.getCount());

       }
    }


}
