package store.view;

import store.model.Inventory;
import store.model.Product;

import java.util.List;

public class OutputView {
    private static final String PRINT_ENTRY_HELLO = "안녕하세요. W편의점입니다.";
    private static final String PRINT_ENTRY_INVENTORY = "현재 보유하고 있는 상품입니다.";
    private static final String REQUEST_PURCHASE_INPUT = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String REQUEST_ADD_PRODUCT = "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n";
    private static final String REQUEST_BUY_PRODUCT = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n";
    private static final String REQUEST_APPLY_MEMBERSHIP_DISCOUNT = "멤버십 할인을 받으시겠습니까? (Y/N)";
    private static final String REQUEST_REPURCHASE = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";
    public void printEntry(Inventory inventory) {
        printWelcomeMessage();
        System.out.println();
        printProductInventory(inventory);
        System.out.println();
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

    public void printAdditionConfirm(String name, int quantity) {
        System.out.println();
        System.out.printf(REQUEST_ADD_PRODUCT, name, quantity);
    }

    public void printBuyConfirm(String name, int quantity) {
        System.out.println();
        System.out.printf(REQUEST_BUY_PRODUCT, name, quantity);
    }

    public void printMembershipConfirm() {
        System.out.println();
        System.out.println(REQUEST_APPLY_MEMBERSHIP_DISCOUNT);
    }

    public void printReceipt(List<String> receipt) {
        System.out.println();
        for (String receiptLine : receipt) {
            System.out.println(receiptLine);
        }
    }

    public void printRepurchaseConfirm() {
        System.out.println();
        System.out.println(REQUEST_REPURCHASE);
    }
}
