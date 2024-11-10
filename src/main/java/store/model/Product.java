package store.model;

import java.text.DecimalFormat;

public record Product(String name, int price, int stock, String promotion) {

    public Product sellProduct(int quantity) {
        if (stock - quantity < 0) {

            return new Product(name, price, 0, promotion);
        }

        return new Product(name, price, stock - quantity, promotion);
    }

    @Override
    public String toString() {
        DecimalFormat priceFormat = new DecimalFormat("#,###");
        String priceInfo = "재고 없음 ";
        String promotionInfo = "";
        if (price != 0) {
            priceInfo = priceFormat.format(price) + "원 ";
        }
        if (promotion != null) {
            promotionInfo = promotion;
        }
        return "- " + name + " " + priceInfo + stock + "개 " + promotionInfo;
    }

    public boolean compareName(String targetName) {

        return name.equals(targetName);
    }

    public boolean compareStock(int quantity) {

        return stock >= quantity;
    }


    public boolean isExistPromotion() {

        return promotion != null;
    }
}
