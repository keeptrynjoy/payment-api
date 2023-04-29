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
     * 상품 리스트 페이징 조회
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
    public ItemPageableDto pagedItemSortByRegisteredAt(int pageNum, int itemSize) {

        PageRequest pageRequest = PageRequest.of(pageNum, itemSize, Sort.by("registerDate"));
        Page<Item> itemPage = itemRepository.findAll(pageRequest);


        int totalPage = itemPage.getTotalPages();
        int lastPageNum = totalPage -1;
        int totalItems = Math.toIntExact(itemPage.getTotalElements());

        /*
         * 1. 등록된 상품 유무 검증
         * 2. 요청한 페이지 번호 검증
         */
        if(totalItems == 0){
            throw ErrorCode.throwNoContentItem();

        } else if(lastPageNum < pageNum){
            throw ErrorCode.throwNotFoundPageNum();

        }


        List<ItemDto> itemList;
        int currentPageNum;

        // 마지막 페이지 요청시 아이템을 시 이전 페이지 데이터 반환
        if (!itemPage.hasNext() && !itemPage.hasContent()) {
            Page<Item> previousPage = itemRepository.findAll(pageRequest.previousOrFirst());
            itemList = getCollect(previousPage);
            currentPageNum = previousPage.getNumber();

        } else {
            itemList = getCollect(itemPage);
            currentPageNum = itemPage.getNumber();

        }

        return ItemPageableDto.builder()
                .totalItems(totalItems)
                .items(itemList)
                .totalPages(totalPage)
                .currentPageNum(currentPageNum)
                .lastPageNum(lastPageNum)
                .build();
    }

    private static List<ItemDto> getCollect(Page<Item> itemPage) {
        return itemPage.stream().map(Item::toItemDto).collect(Collectors.toList());
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
