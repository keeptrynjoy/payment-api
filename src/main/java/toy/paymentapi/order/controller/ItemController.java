package toy.paymentapi.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toy.paymentapi.order.controller.request.ItemRegisterRequest;
import toy.paymentapi.order.controller.response.ItemInfoResponse;
import toy.paymentapi.order.controller.response.ItemListResponse;
import toy.paymentapi.order.service.ItemService;
import toy.paymentapi.order.service.dto.ItemDto;
import toy.paymentapi.order.service.dto.RegisteredItemDto;

import javax.validation.Valid;
import java.util.List;

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


    @GetMapping("list/{pageNum}/{itemSize}")
    public ResponseEntity<ItemListResponse> itemList(@PathVariable("pageNum") Integer pageNum,
                                                     @PathVariable("itemSize") Integer itemSize){

        List<ItemDto> itemDtos = itemService.getItemList(pageNum, itemSize);

        return ResponseEntity.ok(new ItemListResponse(itemDtos));
    }

}
