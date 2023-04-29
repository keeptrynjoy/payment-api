package toy.paymentapi.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;
import toy.paymentapi.order.controller.request.ItemRegisterRequest;
import toy.paymentapi.order.controller.response.ItemInfoResponse;
import toy.paymentapi.order.controller.response.PagedItemsResponse;
import toy.paymentapi.order.service.ItemService;
import toy.paymentapi.order.service.dto.ItemPageableDto;
import toy.paymentapi.order.service.dto.RegisteredItemDto;
import toy.paymentapi.support.error.ErrorCode;
import toy.paymentapi.support.error.GlobalExceptionHandler;
import toy.paymentapi.support.error.PaymentApiException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
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
    public ResponseEntity<PagedItemsResponse> pagedItems(
            @PathVariable(value = "pageNum",required = false) Integer pageNum,
            @PathVariable(value = "itemSize",required = false) Integer itemSize){

        //URI 공백 입력시 예외 처리
        if(pageNum == null || itemSize == null){
            throw ErrorCode.throwEmptyRestUri();
        }

        ItemPageableDto itemPageableDto = itemService.pagedItemSortByRegisteredAt(pageNum, itemSize);

        return ResponseEntity.ok(new PagedItemsResponse(itemPageableDto));
    }
}
