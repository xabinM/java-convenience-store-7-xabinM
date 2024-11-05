package store.view;

import store.model.Product;

import java.util.List;

public class OutputView {
    private static final String PRINT_ENTRY_HELLO = "안녕하세요. W편의점입니다.";
    private static final String PRINT_ENTRY_INVENTORY = "현재 보유하고 있는 상품입니다.";

    public static void outputEntry(List<Product> products) {
        printWelcomeMessage();
        System.out.println();
        printProductInventory(products);
    }

    private static void printWelcomeMessage() {
        System.out.println(PRINT_ENTRY_HELLO);
        System.out.println(PRINT_ENTRY_INVENTORY);
    }

    private static void printProductInventory(List<Product> products) {
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }
}
