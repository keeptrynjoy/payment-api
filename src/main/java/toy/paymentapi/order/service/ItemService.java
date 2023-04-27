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
import toy.paymentapi.order.service.dto.RegisteredItemDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;


    public RegisteredItemDto registeredItem(ItemDto itemInfo){

        Item item = Item.fromItemDto(itemInfo);

        Item createdItem = itemRepository.save(item);

        return new RegisteredItemDto(createdItem);
    }


    public List<ItemDto> getItemList(Integer pageNum, Integer itemSize){
        PageRequest pageRequest = PageRequest.of(pageNum,itemSize, Sort.by("registerDate"));
        Page<Item> items = itemRepository.findAll(pageRequest);

        return items.stream()
                .map(item -> item.toItemDto())
                .collect(Collectors.toList());
    }
}
