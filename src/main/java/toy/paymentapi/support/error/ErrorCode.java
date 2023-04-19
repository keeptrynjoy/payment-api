package toy.paymentapi.support.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    NOT_ENOUGH_STOCK_QUANTITY(BAD_REQUEST,"주문 요청한 상품의 수량보다 재고가 부족합니다."),
    ITEM_STOCK_EMPTY(BAD_REQUEST,"주문한 상품의 보유 재고가 없습니다."),
    NOT_ENOUGH_POINT(BAD_REQUEST,"사용할 포인트가 부족합니다.");

    private final HttpStatus httpStatus;
    private final String detail;

}
