package store.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final String filePath;

    public Inventory() {
        this.filePath = "src/main/resources/products.md";
    }

    public List<Product> loadProductsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            skipHeader(br);

            return br.lines().map(this::parseProduct).toList();
        } catch (IOException e) {
            System.err.println("파일을 읽는 도중 에러가 발생했습니다." + e.getMessage());
            return new ArrayList<>();
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

            return "";
        }
        return promotion;
    }
}
