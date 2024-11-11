package store.model;

import store.exception.Exception;

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
                .orElseThrow(() -> new IllegalArgumentException(Exception.PRODUCT_NOT_FOUND.getMessage()));
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
