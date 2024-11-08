package store.model;

import java.text.DecimalFormat;

public record Product(String name, int price, int stock, String promotion) {

    public Product sellProduct(int quantity) {

        return new Product(name, price, stock - quantity, promotion);
    }

    @Override
    public String toString() {
        DecimalFormat priceFormat = new DecimalFormat("#,###");

        if (promotion == null) {

            return "- " + name + " " + priceFormat.format(price) + "원 " + stock + "개 ";
        }

        return "- " + name + " " + priceFormat.format(price) + "원 " + stock + "개 " + promotion;
    }

    public boolean compareName(String targetName) {

        return name.equals(targetName);
    }

    public boolean compareStock(int quantity) {

        return stock > quantity;
    }

    public boolean isExistPromotion() {

        return promotion == null;
    }

    public boolean comparePromotion(String targetPromotion) {

        return promotion.equals(targetPromotion);
    }
}
