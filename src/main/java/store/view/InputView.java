package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.model.Inventory;
import store.model.WishProduct;
import static store.validator.WishProductValidator.*;


import java.util.ArrayList;
import java.util.List;

public class InputView {
    private final Inventory inventory;

    public InputView(Inventory inventory) {
        this.inventory = inventory;
    }

    public List<WishProduct> requestPurchase(Inventory inventory) {
        String input = Console.readLine().trim();

        validateInputFormat(input);

        return listing(input);
    }

    private List<WishProduct> listing(String input) {
        List<WishProduct> result = new ArrayList<>();

        String[] parts = input.split(",");
        for (String part : parts) {
            result.add(parseWishProduct(part));
        }

        return result;
    }

    private WishProduct parseWishProduct(String part) {
        String[] items = part.replaceAll("[\\[\\]]", "").split("-");

        String name = items[0];
        String stringQuantity = items[1];

        validateParseInteger(stringQuantity);

        int quantity = Integer.parseInt(stringQuantity);
        validateWishProduct(name, quantity, inventory);

        return new WishProduct(name, quantity);
    }

    public String requestAdditionConfirm() {
        String input = Console.readLine().trim();
        validateInputYN(input);

        return input;
    }

    public String requestBuyConfirm() {
        String input = Console.readLine().trim();
        validateInputYN(input);

        return input;
    }

    public String requestMemberShipConfirm() {
        String input = Console.readLine().trim();
        validateInputYN(input);

        return input;
    }

    public String requestRepurchaseConfirm() {
        String input = Console.readLine().trim();
        validateInputYN(input);

        return input;
    }

    private void validateInputYN(String input) {
        if (!input.equals("Y") && !input.equals("N")) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }
}
