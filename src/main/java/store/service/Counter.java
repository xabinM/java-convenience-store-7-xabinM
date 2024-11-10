package store.service;

import store.model.*;
import store.view.InputView;
import store.view.OutputView;

import java.time.LocalDate;
import java.util.List;

public class Counter {
    private final Inventory inventory;
    private final Promotions promotions;
    private final InputView inputView;
    private final OutputView outputView;

    public Counter(Inventory inventory, Promotions promotions) {
        this.inventory = inventory;
        this.promotions = promotions;
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public boolean checkIsPromotionProduct(String name) {
        Product matchingProduct = inventory.findProductByName(name);

        return matchingProduct.isExistPromotion();
    }

    public ResultDTO checkPromotionPeriod(WishProduct wishProduct) {
        Product product = inventory.findProductByName(wishProduct.name());
        Promotion promotion = promotions.findPromotionByName(product.promotion());
        if (promotion.isDateInRange(LocalDate.now())) {
            return checkTotalStock(wishProduct);
        }
        // 근데 이 프로모션 기간이 아니라는 메시지를 던질 필요가 있나? 없지.
        // 그냥 일반 상품 처리하면됨 잠시 대기
        //todo
        throw new IllegalArgumentException("프로모션 기간에 해당하지 않는 상품입니다.");
    }

    private ResultDTO checkTotalStock(WishProduct wishProduct) {
        int totalStock = inventory.findTotalStockByName(wishProduct.name());

        if (totalStock > wishProduct.quantity()) {
            return checkPromotionStock(wishProduct);
        }
        throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }

    private ResultDTO checkPromotionStock(WishProduct wishProduct) {
        Product promotionProduct = inventory.findProductByName(wishProduct.name());

        if (promotionProduct.compareStock(wishProduct.quantity())) {
            return checkFitPromotionBenefit(wishProduct);
        }
        return handleOverPromotionStock(wishProduct);
    }

    public ResultDTO handleOverPromotionStock(WishProduct wishProduct) {
        List<Product> products = inventory.findProductsByName(wishProduct.name());
        Product productOnPromotion = products.getFirst();
        Product productNonPromotion = products.getLast();
        Promotion promotion = promotions.findPromotionByName(productOnPromotion.promotion());
        int overQuantity = wishProduct.quantity() - productOnPromotion.stock();
        int unApplied = (wishProduct.quantity() - overQuantity) % (promotion.buy() + promotion.get());

        return decidePurchaseOrNo(wishProduct, overQuantity, unApplied, productNonPromotion, productOnPromotion);
    }

    private ResultDTO decidePurchaseOrNo(WishProduct wishProduct, int overQuantity
            , int unApplied, Product productNonPromotion, Product productOnPromotion) {
        while (true) {
            try {
                outputView.printBuyConfirm(wishProduct.name(), overQuantity + unApplied);
                return requestPurchaseOrNo(wishProduct, overQuantity, unApplied,
                        productNonPromotion, productOnPromotion);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private ResultDTO requestPurchaseOrNo(WishProduct wishProduct, int overQuantity,
                                          int unApplied, Product productNonPromotion, Product productOnPromotion) {
        String input = inputView.requestBuyConfirm();
        if (input.equals("Y")) {
            inventory.updateProduct(inventory.findIndexByProduct(productNonPromotion),
                    productNonPromotion.sellProduct(overQuantity));
            return reduceStock(productOnPromotion.name(), wishProduct.quantity());
        }
        if (input.equals("N")) {
            WishProduct newWishProduct = wishProduct.decreaseQuantity(overQuantity + unApplied);
            return reduceStock(newWishProduct.name(), newWishProduct.quantity());
        }
        return null;
    }

    private ResultDTO checkFitPromotionBenefit(WishProduct wishProduct) {
        Product product = inventory.findProductByName(wishProduct.name());
        Promotion promotion = promotions.findPromotionByName(product.promotion());
        if (promotion.isFitQuantity(wishProduct.quantity())) {
            return reduceStock(wishProduct.name(), wishProduct.quantity());
        }

        return checkLessBenefit(wishProduct, promotion);
    }

    private ResultDTO checkLessBenefit(WishProduct wishProduct, Promotion promotion) {
        Product product = inventory.findProductByName(wishProduct.name());

        if (product.stock() == wishProduct.quantity() ||
                wishProduct.quantity() % (promotion.buy() + promotion.get()) != promotion.buy()) {
            return reduceStock(wishProduct.name(), wishProduct.quantity());
        }
        return decideAddOrNo(wishProduct, promotion.get());
    }

    private ResultDTO decideAddOrNo(WishProduct wishProduct, int bonus) {
        while (true) {
            try {
                outputView.printAdditionConfirm(wishProduct.name(), bonus);
                return requestAddOrNo(wishProduct, bonus);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private ResultDTO requestAddOrNo(WishProduct wishProduct, int bonus) {
        String input = inputView.requestAdditionConfirm();
        if (input.equals("Y")) {
            WishProduct newWishProduct = wishProduct.increaseQuantity(bonus);
            return reduceStock(newWishProduct.name(), newWishProduct.quantity());
        }
        if (input.equals("N")) {
            return reduceStock(wishProduct.name(), wishProduct.quantity());
        }
        return null;
    }

    public ResultDTO checkNormalStock(WishProduct wishProduct) {
        Product product = inventory.findProductByName(wishProduct.name());

        if (product.compareStock(wishProduct.quantity())) {
            return reduceStock(wishProduct.name(), wishProduct.quantity());
        }
        throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");

    }

    private ResultDTO reduceStock(String name, int quantity) {
        Product product = inventory.findProductByName(name);

        int indexInInventory = inventory.findIndexByProduct(product);
        Product soldProduct = product.sellProduct(quantity);

        inventory.updateProduct(indexInInventory, soldProduct);

        return checkOut(soldProduct, quantity, product.stock());
    }

    private ResultDTO checkOut(Product soldProduct, int quantity, int preStock) {
        if (checkIsPromotionProduct(soldProduct.name())) {
            Promotion promotion = promotions.findPromotionByName(soldProduct.promotion());

            return new ResultDTO(soldProduct.name()
                    , soldProduct.price(), quantity
                    , true, promotion.buy(), promotion.get(), preStock);
        }
        return new ResultDTO(soldProduct.name()
                , soldProduct.price(), quantity
                , false, 0, 0, preStock);
    }
}
