package toy.paymentapi.order.service.dto;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ItemPageableDto {
    private Integer totalItems;
    private List<ItemDto> items;
    private Integer totalPages;
    private Integer currentPage;
}
