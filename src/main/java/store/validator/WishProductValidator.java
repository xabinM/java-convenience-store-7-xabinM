package store.validator;

import store.exception.Exception;
import store.model.Inventory;

public class WishProductValidator {
    public static void validateWishProduct(String name, int inputQuantity, Inventory inventory) {
        validateName(name, inventory);
        validateQuantity(name, inputQuantity, inventory);
    }

    public static void validateName(String inputName, Inventory inventory) {
        if (inventory.inventory().stream()
                .noneMatch(product -> product.compareName(inputName))) {
            throw new IllegalArgumentException(Exception.PRODUCT_NOT_FOUND.getMessage());
        }
    }

    public static void validateQuantity(String name, int inputQuantity, Inventory inventory) {
        int totalStock = inventory.findTotalStockByName(name);

        if (inputQuantity > totalStock) {
            throw new IllegalArgumentException(Exception.STOCK_EXCEEDED.getMessage());
        }
    }

    public static void validateInputFormat(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(Exception.INVALID_FORMAT.getMessage());
        }
        String[] parts = input.split(",");
        for (String part : parts) {
            validatePartFormat(part.trim());
        }
    }

    private static void validatePartFormat(String part) {
        if (!part.matches("\\[[\\w가-힣]+-\\d+\\]")) {
            throw new IllegalArgumentException(Exception.INVALID_FORMAT.getMessage());
        }
    }

    public static void validateParseInteger(String quantity) {
        try {
            int qty = Integer.parseInt(quantity);
            if (qty <= 0) {
                throw new IllegalArgumentException(Exception.INVALID_FORMAT.getMessage());
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(Exception.INVALID_FORMAT.getMessage());
        }
    }
}
