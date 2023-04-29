package toy.paymentapi.order.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import toy.paymentapi.order.service.dto.ItemPageableDto;

@Getter
@AllArgsConstructor
public class PagedItemsResponse {
    private ItemPageableDto pagedItems;
}
