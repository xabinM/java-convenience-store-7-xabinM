package store.controller;

import store.model.*;
import store.service.Counter;
import store.service.ReceiptMachine;
import store.view.InputView;
import store.view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class ConvenienceStore {
    private final Counter counter;
    private final Inventory inventory;
    private final InputView inputView;
    private final OutputView outputView;

    public ConvenienceStore(Counter counter, Inventory inventory, Promotions promotions) {
        this.counter = counter;
        this.inventory = inventory;
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void runStore() {
        processDisplayProducts();
        List<WishProduct> wishProducts = processRequestPurchase();
        List<ResultDTO> result = processPromotionAndStock(wishProducts);
        processDisplayReceipt(result);
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
            return counter.checkPromotionPeriod(wishProduct);
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

    private void processDisplayReceipt(List<ResultDTO> result) {
        List<String> receipt = new ReceiptMachine(processRequestMemberShip()).makeReceipt(result);
        outputView.printReceipt(receipt);
    }

    private boolean processRequestMemberShip() {
        while (true){
            try {
                outputView.printMembershipConfirm();
                return inputView.requestMemberShipConfirm().equals("Y");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
