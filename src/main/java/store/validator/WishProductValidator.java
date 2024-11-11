package store.validator;

import store.model.Inventory;

public class WishProductValidator {
    public static void validateWishProduct(String name, int inputQuantity, Inventory inventory) {
        validateName(name, inventory);
        validateQuantity(name, inputQuantity, inventory);
    }

    public static void validateName(String inputName, Inventory inventory) {
        if (inventory.inventory().stream()
                .noneMatch(product -> product.compareName(inputName))) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
    }

    public static void validateQuantity(String name, int inputQuantity, Inventory inventory) {
        int totalStock = inventory.findTotalStockByName(name);

        if (inputQuantity > totalStock) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    public static void validateInputFormat(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.1");
        }
        String[] parts = input.split(",");
        for (String part : parts) {
            validatePartFormat(part.trim());
        }
    }

    private static void validatePartFormat(String part) {
        if (!part.matches("\\[[\\w가-힣]+-\\d+\\]")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.2");
        }
    }

    public static void validateParseInteger(String quantity) {
        try {
            int qty = Integer.parseInt(quantity);
            if (qty <= 0) {
                throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.3");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.4");
        }
    }
}
