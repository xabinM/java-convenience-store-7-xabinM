package store.model;

import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PromotionLoaderTest {

    @Test
    @DisplayName("파일에서 프로모션 정보가 제대로 로드되는지 테스트")
    void loadPromotionsFromFileTest() {
        PromotionLoader loader = new PromotionLoader();

        List<Promotion> promotions = loader.loadPromotionFromFile();

        assertNotNull(promotions);
        assertFalse(promotions.isEmpty());

        Promotion firstPromotion = promotions.get(0);
        assertEquals("탄산2+1", firstPromotion.name());
        assertEquals(2, firstPromotion.buy());
        assertEquals(1, firstPromotion.get());
        assertEquals("2024-01-01", firstPromotion.startDate().toString());
        assertEquals("2024-12-31", firstPromotion.endDate().toString());

        Promotion secondPromotion = promotions.get(1);
        assertEquals("MD추천상품", secondPromotion.name());
        assertEquals(1, secondPromotion.buy());
        assertEquals(1, secondPromotion.get());
        assertEquals("2024-01-01", secondPromotion.startDate().toString());
        assertEquals("2024-12-31", secondPromotion.endDate().toString());
    }
}
