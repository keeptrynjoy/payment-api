package toy.paymentapi.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toy.paymentapi.order.controller.request.ItemRegisterRequest;
import toy.paymentapi.order.controller.response.ItemInfoResponse;
import toy.paymentapi.order.controller.response.PagedItemsResponse;
import toy.paymentapi.order.service.ItemService;
import toy.paymentapi.order.service.dto.ItemPageableDto;
import toy.paymentapi.order.service.dto.RegisteredItemDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/item")
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @PostMapping("register")
    public ResponseEntity<ItemInfoResponse> registerItem(
           @RequestBody @Valid ItemRegisterRequest itemRegister){

        RegisteredItemDto registeredItemDto = itemService.registeredItem(
                itemRegister.toItemRegisterDto()
        );

        return ResponseEntity.ok(registeredItemDto.toResponse());
    }

    @GetMapping("paged/{pageNum}/{itemSize}")
    public ResponseEntity<PagedItemsResponse> pagedItems(@PathVariable("pageNum") @NotNull Integer pageNum,
                                                         @PathVariable("itemSize") @NotNull Integer itemSize){

        ItemPageableDto itemPageableDto = itemService.pagedItemSortByRegisteredAt(pageNum, itemSize);

        return ResponseEntity.ok(new PagedItemsResponse(itemPageableDto));
    }
}
