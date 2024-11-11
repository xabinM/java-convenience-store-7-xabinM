package store.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    @DisplayName("재고가 충분할 경우, 상품 판매 후 재고가 정확하게 업데이트된다.")
    void testSellProduct_whenStockIsSufficient() {
        Product product = new Product("콜라", 1000, 10, "탄산2+1");
        Product soldProduct = product.sellProduct(5);

        assertEquals(5, soldProduct.stock());
        assertEquals("콜라", soldProduct.name());
        assertEquals(1000, soldProduct.price());
        assertEquals("탄산2+1", soldProduct.promotion());
    }

    @Test
    @DisplayName("재고가 부족할 경우, 상품 판매 후 재고는 0으로 설정된다.")
    void testSellProduct_whenStockIsInsufficient() {
        Product product = new Product("콜라", 1000, 3, "탄산2+1");
        Product soldProduct = product.sellProduct(5);

        assertEquals(0, soldProduct.stock());
        assertEquals("콜라", soldProduct.name());
        assertEquals(1000, soldProduct.price());
        assertEquals("탄산2+1", soldProduct.promotion());
    }

    @Test
    @DisplayName("상품의 정보가 포함된 문자열을 출력할 때, 재고와 프로모션이 포함된다.")
    void testToString_withStockAndPromotion() {
        Product product = new Product("콜라", 1000, 10, "탄산2+1");
        String result = product.toString();

        assertTrue(result.contains("콜라"));
        assertTrue(result.contains("1,000원"));
        assertTrue(result.contains("10개"));
        assertTrue(result.contains("탄산2+1"));
    }

    @Test
    @DisplayName("재고가 없는 상품에 대해, '재고 없음'이 포함된 문자열을 출력한다.")
    void testToString_withNoStock() {
        Product product = new Product("콜라", 1000, 0, "탄산2+1");
        String result = product.toString();

        assertTrue(result.contains("콜라"));
        assertTrue(result.contains("1,000원"));
        assertTrue(result.contains("재고 없음"));
        assertTrue(result.contains("탄산2+1"));
    }

    @Test
    @DisplayName("상품 이름이 일치할 때, compareName()은 true를 반환한다.")
    void testCompareName_whenNamesMatch() {
        Product product = new Product("콜라", 1000, 10, "탄산2+1");

        assertTrue(product.compareName("콜라"));
        assertFalse(product.compareName("사이다"));
    }

    @Test
    @DisplayName("재고가 충분할 경우, compareStock()은 true를 반환한다.")
    void testCompareStock_whenSufficientStock() {
        Product product = new Product("콜라", 1000, 10, "탄산2+1");

        assertTrue(product.compareStock(5));
        assertFalse(product.compareStock(15));
    }

    @Test
    @DisplayName("프로모션이 없을 경우, isExistPromotion()은 false를 반환한다.")
    void testIsExistPromotion_whenPromotionIsNull() {
        Product productWithNoPromotion = new Product("콜라", 1000, 10, null);
        Product productWithPromotion = new Product("사이다", 1000, 10, "탄산2+1");

        assertFalse(productWithNoPromotion.isExistPromotion());
        assertTrue(productWithPromotion.isExistPromotion());
    }
}
