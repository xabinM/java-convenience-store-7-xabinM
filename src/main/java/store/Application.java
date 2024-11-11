package store;

import store.controller.ConvenienceStore;
import store.model.Inventory;
import store.model.InventoryLoader;
import store.model.PromotionLoader;
import store.model.Promotions;
import store.service.Counter;

public class Application {
    public static void main(String[] args) {
        Inventory inventory = new Inventory(new InventoryLoader().loadProductsFromFile());
        Promotions promotions = new Promotions(new PromotionLoader().loadPromotionFromFile());
        ConvenienceStore convenienceStore = new ConvenienceStore(
                new Counter(inventory, promotions), inventory);
        convenienceStore.runStore();
    }
}
