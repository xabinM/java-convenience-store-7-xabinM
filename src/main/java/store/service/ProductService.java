package store.service;

import store.model.Inventory;
import store.model.Product;
import store.model.Promotions;
import store.model.WishProduct;

import java.util.List;

public class ProductService {
    private final Inventory inventory;
    private final Promotions promotions;

    public ProductService(Inventory inventory, Promotions promotions) {
        this.inventory = inventory;

        this.promotions = promotions;
    }

    public boolean checkIsPromotionProduct(WishProduct wishProduct) {
        Product matchingProduct = inventory.findProductByName(wishProduct.name());

        return matchingProduct.isExistPromotion();
    }

    public void checkTotalStock(WishProduct wishProduct) {
        String name = wishProduct.name();
        int quantity = wishProduct.quantity();

        int totalStock = inventory.findTotalStockByName(name);

        if (totalStock > quantity) {
            //Promotion 적용 계산 메서드 호출 ( 2+1 인지, 1+1인지, 기간내인지)
        }
        throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }

}
