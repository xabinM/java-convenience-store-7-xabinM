package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
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
        this.inputView = new InputView(inventory);
        this.outputView = new OutputView();
    }

    public boolean checkIsPromotionProduct(String name) {
        Product matchingProduct = inventory.findProductByName(name);
        return matchingProduct.isExistPromotion();
    }

    public ResultDTO checkPromotionPeriod(WishProduct wishProduct) {
        List<Product> matchingProducts = inventory.findProductsByName(wishProduct.name());
        Product promotionProduct = matchingProducts.getFirst();
        Product normalProduct = matchingProducts.getLast();
        Promotion promotion = promotions.findPromotionByName(promotionProduct.promotion());
        if (promotion.isDateInRange(LocalDate.from(DateTimes.now()))) {
            return checkTotalStock(wishProduct);
        }
        return checkNormalStock(wishProduct, normalProduct);
    }

    private ResultDTO checkTotalStock(WishProduct wishProduct) {
        int totalStock = inventory.findTotalStockByName(wishProduct.name());

        if (totalStock > wishProduct.quantity()) {
            return checkPromotionStock(wishProduct);
        }
        return null;
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
            return reduceStock(wishProduct.quantity(), productOnPromotion);
        }
        if (input.equals("N")) {
            WishProduct newWishProduct = wishProduct.decreaseQuantity(overQuantity + unApplied);
            return reduceStock(newWishProduct.quantity(), productOnPromotion);
        }
        return null;
    }

    private ResultDTO checkFitPromotionBenefit(WishProduct wishProduct) {
        Product product = inventory.findProductByName(wishProduct.name());
        Promotion promotion = promotions.findPromotionByName(product.promotion());
        if (promotion.isFitQuantity(wishProduct.quantity())) {
            return reduceStock(wishProduct.quantity(), product);
        }

        return checkLessBenefit(wishProduct, promotion);
    }

    private ResultDTO checkLessBenefit(WishProduct wishProduct, Promotion promotion) {
        Product product = inventory.findProductByName(wishProduct.name());

        if (product.stock() == wishProduct.quantity() ||
                wishProduct.quantity() % (promotion.buy() + promotion.get()) != promotion.buy()) {
            return reduceStock(wishProduct.quantity(), product);
        }
        return decideAddOrNo(wishProduct, promotion.get(), product);
    }

    private ResultDTO decideAddOrNo(WishProduct wishProduct, int bonus, Product product) {
        while (true) {
            try {
                outputView.printAdditionConfirm(wishProduct.name(), bonus);
                return requestAddOrNo(wishProduct, bonus, product);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private ResultDTO requestAddOrNo(WishProduct wishProduct, int bonus, Product product) {
        String input = inputView.requestAdditionConfirm();
        if (input.equals("Y")) {
            WishProduct newWishProduct = wishProduct.increaseQuantity(bonus);
            return reduceStock(newWishProduct.quantity(), product);
        }
        if (input.equals("N")) {
            return reduceStock(wishProduct.quantity(), product);
        }
        return null;
    }

    public ResultDTO checkNormalStock(WishProduct wishProduct, Product product) {
        if (product.compareStock(wishProduct.quantity())) {
            return reduceStock(wishProduct.quantity(), product);
        }
        return null;
    }

    private ResultDTO reduceStock(int quantity, Product product) {
        int indexInInventory = inventory.findIndexByProduct(product);
        Product soldProduct = product.sellProduct(quantity);
        inventory.updateProduct(indexInInventory, soldProduct);

        return checkOut(soldProduct, quantity, product.stock());
    }

    private ResultDTO checkOut(Product soldProduct, int quantity, int preStock) {
        if (soldProduct.isExistPromotion()) {
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
