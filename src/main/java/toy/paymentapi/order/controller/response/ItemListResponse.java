package toy.paymentapi.order.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import toy.paymentapi.order.service.dto.ItemDto;

import java.util.List;

@Getter
@AllArgsConstructor
public class ItemListResponse {
    List<ItemDto> itemDtos;
}
