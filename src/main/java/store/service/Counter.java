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

        }
        throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }

    // 프로모션 재고로만 계산 가능(재고 감소시 일반재고로 넘겨야하는지)한지 아닌지 검증 메서드
    private ResultDTO checkPromotionStock(WishProduct wishProduct) {
        Product promotionProduct = inventory.findProductByName(wishProduct.name());

        if (promotionProduct.compareStock(wishProduct.quantity())) {
            // 프로모션 재고가 충분하여 굳이 일반재고까지 넘어가서 깎지 않아도 되는 경우
            // 혜택(ex 2+1)으로 나누어 떨어지는 지 검증하고 재고 처리해야됌
            return checkFitPromotionBenefit(wishProduct);
        }
        // 프로모션 재고가 부족하여 일반 재고로 넘어가야되는 경우
        // todo
        // 프로모션 재고를 넘어서는 quantity를 입력받았을때

        return;
    }



    // 프로모션 혜택 적용 quantity 검증
    private ResultDTO checkFitPromotionBenefit(WishProduct wishProduct) {
        Promotion promotion = promotions.findPromotionByName(wishProduct.name());

        if (promotion.isFitQuantity(wishProduct.quantity())) {
            // 2+1 or 1+1 로 나눴는데 딱 나눠떨어지네?
            // 그럼 그냥 재고 계산 호출
            return reduceStock(wishProduct.name(), wishProduct.quantity());
        }
        // 나눠떨어지지가 않네?
        // 그럼 부족한거 더 가져오세요 메서드
        return checkLessBenefit(wishProduct, promotion);
    }

    // quantity가 적어서 혜택을 못받아요 더 가져오실?
    private ResultDTO checkLessBenefit(WishProduct wishProduct, Promotion promotion) {
        int remain = wishProduct.quantity() % (promotion.buy() + promotion.get());
        Product product = inventory.findProductByName(wishProduct.name());

        if (product.stock() == wishProduct.quantity()) {
            return reduceStock(wishProduct.name(), wishProduct.quantity());
        }

        return decideAddOrNo(wishProduct, remain);
    }

    private ResultDTO decideAddOrNo(WishProduct wishProduct, int remain) {
        while (true){
            try {
                outputView.printAdditionConfirm(wishProduct.name(), wishProduct.quantity());
                String input = inputView.requestAdditionConfirm();
                if (input.equals("Y")) {
                    WishProduct newWishProduct = wishProduct.increaseQuantity(remain);

                    return reduceStock(newWishProduct.name(), newWishProduct.quantity());
                }
                if (input.equals("N")) {
                    return reduceStock(wishProduct.name(), wishProduct.quantity());
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // 일반 상품(프로모션 x)인 상품 계산 메서드
    public ResultDTO checkNormalStock(WishProduct wishProduct) {
        Product product = inventory.findProductByName(wishProduct.name());

        if (product.compareStock(wishProduct.quantity())) {
            return reduceStock(wishProduct.name(), wishProduct.quantity());
        }
        throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");

    }

    // 재고 감소시키는 메서드
    private ResultDTO reduceStock(String name, int quantity) {
        Product product = inventory.findProductByName(name);

        int indexInInventory = inventory.findIndexByProduct(product);
        Product soldProduct = product.sellProduct(quantity);

        inventory.updateProduct(indexInInventory, soldProduct);

        return checkOut(soldProduct, quantity, product.stock());
    }

    // 영수증에 쓸 정보 처리 메서드
    private ResultDTO checkOut(Product soldProduct, int quantity, int preStock) {
        if (checkIsPromotionProduct(soldProduct.name())) {
            Promotion promotion = promotions.findPromotionByName(soldProduct.promotion());

            return new ResultDTO(soldProduct.name(), soldProduct.price()
                    , quantity, promotion.buy(), promotion.get(), preStock);
        }
        return new ResultDTO(soldProduct.name()
                , soldProduct.price(), quantity
                , 0, 0, preStock);
    }
}
