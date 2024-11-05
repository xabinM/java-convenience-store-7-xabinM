package store.model;

public class Product {
    private final String name;
    private final int price;
    private final int quantity;
    private final String promotion;

    public Product(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public Product sellProduct(int count) {
        if (count > quantity) {
            throw new IllegalArgumentException("재고 부족");
        }

        return new Product(name, price, quantity - count, promotion);
    }
}
