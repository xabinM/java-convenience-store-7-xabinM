package store.service;

import store.model.Promotion;
import store.model.PromotionLoader;

import java.util.List;

public class PromotionService {
    private final List<Promotion> promotions;

    public PromotionService() {
        this.promotions = new PromotionLoader().loadPromotionFromFile();
    }
}
