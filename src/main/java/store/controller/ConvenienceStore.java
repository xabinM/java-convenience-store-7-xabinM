package store.controller;

import store.model.*;
import store.service.Counter;
import store.service.PromotionService;
import store.view.InputView;
import store.view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class ConvenienceStore {
    private final Counter counter;
    private final PromotionService promotionService;
    private final Inventory inventory;
    private final Promotions promotions;
    private final InputView inputView;
    private final OutputView outputView;

    public ConvenienceStore(Counter counter, PromotionService promotionService, Inventory inventory, Promotions promotions) {
        this.counter = counter;
        this.promotionService = promotionService;
        this.inventory = inventory;
        this.promotions = promotions;
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void runStore() {
        processDisplayProducts();
        List<WishProduct> wishProducts = processRequestPurchase();
        List<ResultDTO> result = processPromotionAndStock(wishProducts);
    }

    private void processDisplayProducts() {;
        outputView.printEntry(inventory);
    }

    private List<WishProduct> processRequestPurchase() {
        outputView.printRequestPurchase();

        return inputView.requestPurchase();
    }

    private List<ResultDTO> processPromotionAndStock(List<WishProduct> wishProducts) {
        final List<ResultDTO> result = new ArrayList<>();

        // 여기에서 각 process들이 DTO 객체를 뱉고 그 객체를 리스트로 싸서 리턴 시키면 될듯
        for (WishProduct wishProduct : wishProducts) {
            if (counter.checkIsPromotionProduct(wishProduct.name())) {
                result.add(processOnPromotion(wishProduct));
            } else {
                result.add(processNonPromotion(wishProduct));
            }
        }
        return result;
    }

    private ResultDTO processOnPromotion(WishProduct wishProduct) {
        try {
            counter.checkPromotionPeriod(wishProduct);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private ResultDTO processNonPromotion(WishProduct wishProduct) {
        try {
            return counter.checkNormalStock(wishProduct);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
