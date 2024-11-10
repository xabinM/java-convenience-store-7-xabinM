package store.model;

public enum Receipt {

    HISTORY_START_BAR("==============W 편의점================"),
    ITEM_HISTORY("상품명\t\t수량\t금액"),
    FREE_GIFT_BAR("=============증\t정==============="),
    HISTORY_LAST_BAR("===================================="),
    TOTAL_PURCHASE("총구매액"),
    EVENT_DISCOUNT("행사할인"),
    MEMBERSHIP_DISCOUNT("멤버십할인"),
    FINAL_AMOUNT("내실돈");

    private final String output;

    Receipt(String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }
}
