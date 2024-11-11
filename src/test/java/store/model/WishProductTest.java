package store.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class WishProductTest {

    private WishProduct wishProduct;

    @BeforeEach
    void setUp() {
        wishProduct = new WishProduct("콜라", 10);
    }

    @Test
    @DisplayName("수량을 감소시킬 때 새로운 WishProduct가 반환되는지 테스트")
    void decreaseQuantityTest() {
        WishProduct updatedProduct = wishProduct.decreaseQuantity(3);

        assertNotNull(updatedProduct);
        assertEquals("콜라", updatedProduct.name());
        assertEquals(7, updatedProduct.quantity());
    }

    @Test
    @DisplayName("수량을 증가시킬 때 새로운 WishProduct가 반환되는지 테스트")
    void increaseQuantityTest() {
        WishProduct updatedProduct = wishProduct.increaseQuantity(5);

        assertNotNull(updatedProduct);
        assertEquals("콜라", updatedProduct.name());
        assertEquals(15, updatedProduct.quantity());
    }

}
