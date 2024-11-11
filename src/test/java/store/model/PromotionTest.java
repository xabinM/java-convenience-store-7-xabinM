package store.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PromotionTest {

    @DisplayName("날짜가 프로모션 기간에 포함되는지 테스트")
    @Test
    public void testIsDateInRange() {
        Promotion promotion = new Promotion("탄산2+1", 1, 1, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31));
        LocalDate validDate = LocalDate.of(2024, 6, 1);
        LocalDate invalidDateBefore = LocalDate.of(2023, 12, 31);
        LocalDate invalidDateAfter = LocalDate.of(2025, 1, 1);
        assertTrue(promotion.isDateInRange(validDate));
        assertFalse(promotion.isDateInRange(invalidDateBefore));
        assertFalse(promotion.isDateInRange(invalidDateAfter));
    }
}
