package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.model.WishProduct;

import java.util.ArrayList;
import java.util.List;

public class InputView {
    public List<WishProduct> requestPurchase() {
        String input = Console.readLine();

        return listing(input.trim());
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

        return new WishProduct(items[0], Integer.parseInt(items[1]));
    }
}
