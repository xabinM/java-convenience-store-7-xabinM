package store.model;

public record WishProduct(String name, int quantity) {
    public WishProduct decreaseQuantity(int overQuantity) {

        return new WishProduct(name, quantity - overQuantity);
    }

    public WishProduct increaseQuantity(int lessQuantity) {

        return new WishProduct(name, quantity + lessQuantity);
    }
}
