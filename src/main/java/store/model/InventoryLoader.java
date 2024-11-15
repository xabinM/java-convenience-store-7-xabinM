package store.model;

import store.exception.Exception;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryLoader {
    private final String filePath;

    public InventoryLoader() {
        this.filePath = "src/main/resources/products.md";
    }

    public List<Product> loadProductsFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            skipHeader(br);

            return br.lines().map(this::parseProduct).collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException(Exception.INVALID_FILE_FORMAT.getMessage());
        }
    }

    private void skipHeader(BufferedReader br) throws IOException {
        br.readLine();
    }

    private Product parseProduct(String line) {
        String[] parts = line.split(",");
        String name = parts[0].trim();
        int price = Integer.parseInt(parts[1].trim());
        int quantity = Integer.parseInt(parts[2].trim());
        String promotion = parsePromotion(parts[3].trim());

        return new Product(name, price, quantity, promotion);
    }

    private String parsePromotion(String promotion) {
        if ("null".equals(promotion)) {

            return null;
        }
        return promotion;
    }
}
