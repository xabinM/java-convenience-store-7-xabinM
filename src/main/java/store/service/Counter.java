package store.service;

import store.model.*;

import java.time.LocalDate;
import java.util.List;

public class Counter {
    private final Inventory inventory;
    private final Promotions promotions;

    public Counter(Inventory inventory, Promotions promotions) {
        this.inventory = inventory;
        this.promotions = promotions;
    }

    public boolean checkIsPromotionProduct(WishProduct wishProduct) {
        Product matchingProduct = inventory.findProductByName(wishProduct.name());

        return matchingProduct.isExistPromotion();
    }

    public void checkPromotionPeriod(WishProduct wishProduct) {
        Promotion promotion = promotions.findPromotionByName(wishProduct.name());

        if (promotion.isDateInRange(LocalDate.now())) {
            checkTotalStock(wishProduct);
        }
        // 근데 이 프로모션 기간이 아니라는 메시지를 던질 필요가 있나? 없지.
        // 그냥 일반 상품 처리하면됨 잠시 대기
        throw new IllegalArgumentException("프로모션 기간에 해당하지 않는 상품입니다.");
    }

    private void checkTotalStock(WishProduct wishProduct) {
        int totalStock = inventory.findTotalStockByName(wishProduct.name());

        if (totalStock > wishProduct.quantity()) {
            //Promotion 적용 계산 메서드 호출 ( 2+1 인지, 1+1인지, 기간내인지)

        }
        throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }

    public void checkStock(WishProduct wishProduct) {
        Product product = inventory.findProductByName(wishProduct.name());

        if (product.compareStock(wishProduct.quantity())) {
            checkOut(wishProduct);
        }
        throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");

    }

    public void checkOut(WishProduct wishProduct) {
        List<Product> products = inventory.findProductsByName(wishProduct.name());
        for (Product product : products) {
            int indexInInventory = inventory.findIndexByProduct(product);
            Product soldProduct = product.sellProduct(wishProduct.quantity());
            inventory.updateProduct(indexInInventory, soldProduct);
        }
    }
}
