package store.controller;

import store.model.*;
import store.service.ProductService;
import store.service.PromotionService;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;

public class ConvenienceStore {
    private final ProductService productService;
    private final PromotionService promotionService;
    private final Inventory inventory;
    private final InputView inputView;
    private final OutputView outputView;

    public ConvenienceStore(ProductService productService, PromotionService promotionService, Inventory inventory) {
        this.productService = productService;
        this.promotionService = promotionService;
        this.inventory = inventory;
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void runStore() {
        processDisplayProducts();
        List<WishProduct> wishProducts = processRequestPurchase();
    }

    private void processDisplayProducts() {;
        outputView.printEntry(inventory);
    }

    private List<WishProduct> processRequestPurchase() {
        outputView.printRequestPurchase();

        return inputView.requestPurchase();
    }
}
