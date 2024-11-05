package store.view;

import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputView {
    public static List<Map<String, Integer>> requestPurchaseProduct() {
        String input = Console.readLine();

        return parseInput(input.trim());
    }

    public static List<Map<String, Integer>> parseInput(String input) {
        List<Map<String, Integer>> result = new ArrayList<>();

        listing(input, result);

        return result;
    }

    private static void listing(String input, List<Map<String, Integer>> result) {
        String[] parts = input.split(",");
        for (String part : parts) {
            Map<String, Integer> itemDetail = new HashMap<>();
            
            String[] items = part.replaceAll("[\\[\\]]", "").split("-");
            itemDetail.put(items[0], Integer.parseInt(items[1]));
            result.add(itemDetail);
        }
    }
}
