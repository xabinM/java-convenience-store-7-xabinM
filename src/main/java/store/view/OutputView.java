package store.view;

import store.model.Inventory;
import store.model.Product;

public class OutputView {
    private static final String PRINT_ENTRY_HELLO = "안녕하세요. W편의점입니다.";
    private static final String PRINT_ENTRY_INVENTORY = "현재 보유하고 있는 상품입니다.";
    private static final String REQUEST_PURCHASE_INPUT = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";

    public void printEntry(Inventory inventory) {
        printWelcomeMessage();
        System.out.println();
        printProductInventory(inventory);
    }

    private void printWelcomeMessage() {
        System.out.println(PRINT_ENTRY_HELLO);
        System.out.println(PRINT_ENTRY_INVENTORY);
    }

    private void printProductInventory(Inventory inventory) {
        for (Product product : inventory.inventory()) {
            System.out.println(product.toString());
        }
    }

    public void printRequestPurchase() {
        System.out.println(REQUEST_PURCHASE_INPUT);
    }

}
