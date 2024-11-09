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
        Product product = inventory.findProductByName(wishProduct.name());
        Promotion promotion = promotions.findPromotionByName(product.promotion());

        if (promotion.isDateInRange(LocalDate.now())) {
            checkTotalStock(wishProduct);
            return;
        }
        // 근데 이 프로모션 기간이 아니라는 메시지를 던질 필요가 있나? 없지.
        // 그냥 일반 상품 처리하면됨 잠시 대기
        throw new IllegalArgumentException("프로모션 기간에 해당하지 않는 상품입니다.");
    }

    private void checkTotalStock(WishProduct wishProduct) {
        int totalStock = inventory.findTotalStockByName(wishProduct.name());

        if (totalStock > wishProduct.quantity()) {
            //Promotion 적용 계산 메서드 호출 ( 2+1 인지, 1+1인지, 기간내인지)
            return;
        }
        throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }

    private ResultDTO checkPromotionStock(WishProduct wishProduct) {
        List<Product> products = inventory.findProductsByName(wishProduct.name());
        Product promotionProduct = products.get(0);
        Product normalProduct = products.get(1);

        if (promotionProduct.compareStock(wishProduct.quantity())) {
            // 프로모션 재고가 충분하여 굳이 일반재고까지 넘어가서 깎지 않아도 되는 경우
            // 그냥 프로모션 재고 깎고 DTO 리턴
            // 아니지 바로 재고 깎는게 아닌 프로모션 상품 개수 검증 해야지
            return reduceStock(wishProduct);
        }
        // 프로모션 재고가 부족하여 일반 재고로 넘어가야되는 경우
        return null;
    }

    // 프로모션 상품 개수 검증


    public ResultDTO checkNormalStock(WishProduct wishProduct) {
        Product product = inventory.findProductByName(wishProduct.name());

        if (product.compareStock(wishProduct.quantity())) {
            return reduceStock(wishProduct);
        }
        throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");

    }

    private ResultDTO reduceStock(WishProduct wishProduct) {
        Product product = inventory.findProductByName(wishProduct.name());

        int indexInInventory = inventory.findIndexByProduct(product);
        Product soldProduct = product.sellProduct(wishProduct.quantity());

        inventory.updateProduct(indexInInventory, soldProduct);

        return checkOut(wishProduct, soldProduct);
    }

    private ResultDTO checkOut(WishProduct wishProduct, Product soldProduct) {
        if (checkIsPromotionProduct(wishProduct)) {
            Promotion promotion = promotions.findPromotionByName(soldProduct.promotion());

            return new ResultDTO(soldProduct.name(), soldProduct.price()
                    , wishProduct.quantity(), promotion.buy(), promotion.get());
        }
        return new ResultDTO(soldProduct.name()
                , soldProduct.price(), wishProduct.quantity()
                , 0, 0);
    }
}
