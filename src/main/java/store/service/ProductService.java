package store.service;

import store.model.*;

import java.time.LocalDate;

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

    public boolean checkPromotionPeriod(WishProduct wishProduct) {
        String name = wishProduct.name();
        Promotion promotion = promotions.findPromotionByName(name);

        if (promotion.isDateInRange(LocalDate.now())) {
            checkTotalStock(wishProduct);
        }
        // 근데 이 프로모션 기간이 아니라는 메시지를 던질 필요가 있나? 없지.
        // 그냥 일반 상품 처리하면됨 잠시 대기
        throw new IllegalArgumentException("프로모션 기간에 해당하지 않는 상품입니다.");
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
