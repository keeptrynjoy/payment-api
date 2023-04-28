package toy.paymentapi.order.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ItemPageableDto {
    private Integer totalItems;
    private List<ItemDto> items;
    private Integer totalPages;
    private Integer currentPageNum;
    private Integer lastPageNum;
}
