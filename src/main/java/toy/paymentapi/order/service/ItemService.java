package toy.paymentapi.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.paymentapi.order.domain.Item;
import toy.paymentapi.order.repository.ItemRepository;
import toy.paymentapi.order.service.dto.ItemDto;
import toy.paymentapi.order.service.dto.ItemPageableDto;
import toy.paymentapi.order.service.dto.RegisteredItemDto;
import toy.paymentapi.support.error.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * 상품 조회
     * parameter
     * - Integer pageNum : 조회 요청한 페이지 번호
     * - Integer itemSize : 한 페이지에 담을 상품 수
     *
     * return
     * - ItemPageableDto :
     *      - 전체 상품 수
     *      - 요청한 페이지 상품 정보(List<ItemDto>)
     *      - 전체 페이지 수
     *      - 요청한 페이지 번호
     *      - 마지막 페이지 번호
     **/
    @Transactional(readOnly = true)
    public ItemPageableDto pagedItemSortByRegisteredAt(Integer pageNum, Integer itemSize){

        PageRequest pageRequest = PageRequest.of(pageNum,itemSize, Sort.by("registerDate"));
        Page<Item> itemPage = itemRepository.findAll(pageRequest);


        List<ItemDto> itemList;
        int currentPageNum;

        if(itemPage.isFirst() && !itemPage.hasContent()){

            throw ErrorCode.throwNoContentItem();

        } else if (!itemPage.hasNext() && !itemPage.hasContent()){

            Page<Item> previousPage = itemRepository.findAll(pageRequest.previousOrFirst());
            itemList = previousPage.stream().map(Item::toItemDto).collect(Collectors.toList());
            currentPageNum = previousPage.getNumber();

        } else {
            itemList = itemPage.stream().map(Item::toItemDto).collect(Collectors.toList());
            currentPageNum = itemPage.getNumber();
        }


        Integer totalItems = Math.toIntExact(itemPage.getTotalElements());
        Integer totalPage = itemPage.getTotalPages();
        Integer lastPageNum = totalPage -1;

        return ItemPageableDto.builder()
                .totalItems(totalItems)
                .items(itemList)
                .totalPages(totalPage)
                .currentPageNum(currentPageNum)
                .lastPageNum(lastPageNum)
                .build();
    }


    /**
    * 상품 등록
    **/
    public RegisteredItemDto registeredItem(ItemDto itemInfo){

        Item item = Item.fromItemDto(itemInfo);

        Item createdItem = itemRepository.save(item);

        return new RegisteredItemDto(createdItem);
    }

}
