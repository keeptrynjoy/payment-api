package toy.paymentapi.support.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    NOT_ENOUGH_STOCK_QUANTITY(BAD_REQUEST,"주문 요청한 상품의 수량보다 재고가 부족합니다."),
    STOCK_EMPTY(BAD_REQUEST,"주문한 상품의 보유 재고가 없습니다."),
    NOT_ENOUGH_POINT(BAD_REQUEST,"사용할 포인트가 부족합니다."),
    NOT_FOUND_ORDER(NOT_FOUND, "주문 내역 확인 불가"),
    NOT_MATCH_AMOUNTS(BAD_REQUEST,"결제 금액 불일치"),

    //== 204, No Content ==//
    NO_CONTENT_ITEM(NO_CONTENT,"등록된 상품이 없습니다.");


    private final HttpStatus httpStatus;
    private final String detail;


    public static PaymentApiException throwNotEnoughStockQuantity(){
        throw new PaymentApiException(NOT_ENOUGH_STOCK_QUANTITY);
    }

    public static PaymentApiException throwStockEmpty(){
        throw new PaymentApiException(STOCK_EMPTY);
    }
    public static PaymentApiException throwNotEnoughPoint(){
        throw new PaymentApiException(NOT_ENOUGH_POINT);
    }
    public static PaymentApiException throwNotFoundOrder(){
        throw new PaymentApiException( NOT_FOUND_ORDER);
    }
    public static PaymentApiException throwNotMatchAmounts(){
        throw new PaymentApiException(NOT_MATCH_AMOUNTS);
    }

    public static PaymentApiException throwNoContentItem(){
        throw new PaymentApiException(NO_CONTENT_ITEM);
    }
}
