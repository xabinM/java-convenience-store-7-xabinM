package store.model;

import java.util.List;

public record Promotions(List<Promotion> promotions) {

    // 이제 여기는 프로모션 이름으로 프로모션리스트에 접근하는곳

    public Promotion findPromotionByName(String name) {

        return promotions.stream()
                .filter(promotion -> promotion.compareName(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 맞는 프로모션이 없습니다."));
    }
}
