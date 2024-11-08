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

    public void printAdditionConfirm() {
        System.out.println("현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
    }

    public void printBuyConfirm(String name, int quantity) {
        System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)", name, quantity);
    }
}