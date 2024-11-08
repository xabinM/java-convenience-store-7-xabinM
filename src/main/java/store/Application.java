package store;

import store.controller.ConvenienceStore;
import store.model.Inventory;
import store.model.InventoryLoader;
import store.service.ProductService;
import store.service.PromotionService;

public class Application {
    public static void main(String[] args) {
        Inventory inventory = new Inventory(new InventoryLoader().loadProductsFromFile());
        ConvenienceStore convenienceStore = new ConvenienceStore(
                new ProductService(inventory), new PromotionService(), inventory);
        convenienceStore.runStore();
    }
}
