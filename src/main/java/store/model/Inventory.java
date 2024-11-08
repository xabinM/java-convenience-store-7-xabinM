package store.model;

import java.util.List;
import java.util.stream.Collectors;

public record Inventory(List<Product> inventory) {

    public void updateProduct(int index, Product newProduct) {

        inventory.set(index, newProduct);
    }

    public Product findProductByName(String name) {

        return inventory.stream()
                .filter(product -> product.compareName(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."));
    }

    public int findTotalStockByName(String name) {

        return inventory.stream()
                .filter(product -> product.compareName(name))
                .mapToInt(Product::stock)
                .sum();
    }

    public List<Product> findProductsByName(String name) {

        return inventory.stream()
                .filter(product -> product.compareName(name))
                .collect(Collectors.toList());
    }

    public int findIndexByProduct(Product product) {

        return inventory.indexOf(product);
    }
}
