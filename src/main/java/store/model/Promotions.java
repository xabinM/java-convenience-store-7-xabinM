package store.model;

import store.exception.Exception;

import java.util.List;

public record Promotions(List<Promotion> promotions) {
    public Promotion findPromotionByName(String name) {

        return promotions.stream()
                .filter(promotion -> promotion.compareName(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(Exception.PROMOTION_NOT_FOUND.getMessage()));
    }
}
