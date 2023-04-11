package toy.paymentapi.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.paymentapi.domain.Item;
import toy.paymentapi.domain.OrderItem;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    Long id;
    int count;


}