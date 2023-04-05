package toy.paymentapi.support.error;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
public enum ErrorCode {

    NOT_ENOUGH_STOCK_QUANTITY(NOT_FOUND,"주문 요청한 상품의 수량보다 재고가 부족합니다."),
    ITEM_STOCK_EMPTY(NOT_FOUND,"주문한 상품의 보유 재고가 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    /* 404 NOT FOUND */
    public static PaymentApiException throwNotEnoughStockQuantity(){
        throw new PaymentApiException(NOT_ENOUGH_STOCK_QUANTITY);
    }
}
