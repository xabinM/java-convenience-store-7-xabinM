package store.controller;

import store.model.Inventory;

import static store.view.OutputView.*;
import static store.view.InputView.*;

public class ConvenienceStore {
    public void runStore() {
        processDisplayProducts();
        processRequestPurchase();
    }

    private void processDisplayProducts() {
        printEntry(new Inventory().loadProductsFromFile());
    }

    private void processRequestPurchase() {
        printRequestPurchase();
        requestPurchaseProduct();
    }
}
