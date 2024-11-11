package store.exception;

public enum Exception {
    INVALID_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    PRODUCT_NOT_FOUND("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    STOCK_EXCEEDED("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    INVALID_INPUT("잘못된 입력입니다. 다시 입력해 주세요."),
    INVALID_FILE_FORMAT("파일을 읽는 도중 에러가 발생했습니다."),
    PROMOTION_NOT_FOUND("맞는 프로모션이 없습니다.");


    private static final String PREFIX = "[ERROR]";
    private final String message;

    Exception(String message) {
        this.message = message;
    }

    public String getMessage() {
        return PREFIX + message;
    }
}
