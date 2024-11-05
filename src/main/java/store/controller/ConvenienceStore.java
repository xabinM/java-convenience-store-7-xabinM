package store.controller;

import store.model.Inventory;
import store.view.OutputView;

public class ConvenienceStore {
    public void runStore() {
        processDisplayProducts();
    }

    private void processDisplayProducts() {
        OutputView.outputEntry(new Inventory().loadProductsFromFile());

    }
}
