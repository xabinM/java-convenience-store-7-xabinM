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

    public ConvenienceStore(Counter counter, Inventory inventory) {
        this.counter = counter;
        this.inventory = inventory;
        this.inputView = new InputView(inventory);
        this.outputView = new OutputView();
    }

    public void runStore() {
        processDisplayProducts();
        List<WishProduct> wishProducts = processRequestPurchase();
        List<ResultDTO> result = processPromotionAndStock(wishProducts);
        processDisplayReceipt(result);
        if (processRepurchase()) {
            runStore();
        }
    }

    private void processDisplayProducts() {;
        outputView.printEntry(inventory);
    }

    private List<WishProduct> processRequestPurchase() {
        while (true){
            try {
                outputView.printRequestPurchase();

                return inputView.requestPurchase(inventory);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private List<ResultDTO> processPromotionAndStock(List<WishProduct> wishProducts) {
        final List<ResultDTO> result = new ArrayList<>();

        for (WishProduct wishProduct : wishProducts) {
            boolean check = counter.checkIsPromotionProduct(wishProduct.name());
            if (check) {
                result.add(processOnPromotion(wishProduct));
            }
            if (!check) {
                result.add(processNonPromotion(wishProduct));
            }
        }
        return result;
    }

    private ResultDTO processOnPromotion(WishProduct wishProduct) {

        return counter.checkPromotionPeriod(wishProduct);
    }

    private ResultDTO processNonPromotion(WishProduct wishProduct) {
        Product product = inventory.findProductByName(wishProduct.name());

        return counter.checkNormalStock(wishProduct, product);
    }

    private void processDisplayReceipt(List<ResultDTO> result) {
        List<String> receipt = new ReceiptMachine(processRequestMemberShip()).makeReceipt(result);
        outputView.printReceipt(receipt);
    }

    private boolean processRequestMemberShip() {
        while (true){
            try {
                outputView.printMembershipConfirm();
                String input = inputView.requestMemberShipConfirm();
                return input.equals("Y");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean processRepurchase() {
        while (true){
            try {
                outputView.printRepurchaseConfirm();
                String input = inputView.requestRepurchaseConfirm();
                return input.equals("Y");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
