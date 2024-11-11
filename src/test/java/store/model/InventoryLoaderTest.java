package store.model;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryLoaderTest {

    @Test
    @DisplayName("파일에서 상품 정보가 제대로 로드되는지 테스트")
    void loadProductsFromFileTest() {
        InventoryLoader loader = new InventoryLoader();

        List<Product> products = loader.loadProductsFromFile();

        assertNotNull(products);
        assertFalse(products.isEmpty());

        Product firstProduct = products.get(0);
        assertEquals("콜라", firstProduct.name());
        assertEquals(1000, firstProduct.price());
        assertEquals(10, firstProduct.stock());
        assertEquals("탄산2+1", firstProduct.promotion());

        Product secondProduct = products.get(1);
        assertEquals("콜라", secondProduct.name());
        assertEquals(1000, secondProduct.price());
        assertEquals(10, secondProduct.stock());
        assertNull(secondProduct.promotion());
    }
}
