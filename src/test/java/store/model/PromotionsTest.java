package store.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PromotionsTest {

    private Promotions promotions;

    @BeforeEach
    void setUp() {
        promotions = new Promotions(new PromotionLoader().loadPromotionFromFile());
    }

    @Test
    @DisplayName("프로모션 이름으로 프로모션을 찾는 메서드 테스트")
    void findPromotionByNameTest() {
        Promotion promotion = promotions.findPromotionByName("MD추천상품");

        assertNotNull(promotion);
        assertEquals("MD추천상품", promotion.name());
    }

    @Test
    @DisplayName("프로모션 이름으로 프로모션을 찾을 수 없을 때 예외가 발생하는지 테스트")
    void findPromotionByNameNotFoundTest() {
        assertThrows(IllegalArgumentException.class, () -> promotions.findPromotionByName("잘못된 프로모션"));
    }
}
