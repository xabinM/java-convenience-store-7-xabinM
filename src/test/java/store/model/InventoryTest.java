package store.model;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory(new InventoryLoader().loadProductsFromFile());
    }

    @Test
    @DisplayName("상품을 업데이트하는 메서드 테스트")
    void updateProductTest() {
        Product newProduct = new Product("콜라", 1000, 20, "탄산2+1");

        inventory.updateProduct(0, newProduct);

        Product updatedProduct = inventory.inventory().get(0);
        assertEquals(20, updatedProduct.stock());
    }

    @Test
    @DisplayName("이름으로 상품을 찾는 메서드 테스트")
    void findProductByNameTest() {
        Product product = inventory.findProductByName("사이다");

        assertNotNull(product);
        assertEquals("사이다", product.name());
        assertEquals(1000, product.price());
        assertEquals(8, product.stock());
    }

    @Test
    @DisplayName("이름으로 상품을 찾을 수 없을 때 예외가 발생하는지 테스트")
    void findProductByNameNotFoundTest() {
        assertThrows(IllegalArgumentException.class, () -> inventory.findProductByName("주스"));
    }

    @Test
    @DisplayName("이름으로 총 재고 수를 구하는 메서드 테스트")
    void findTotalStockByNameTest() {
        int totalStock = inventory.findTotalStockByName("콜라");

        assertEquals(20, totalStock);
    }

    @Test
    @DisplayName("이름으로 여러 상품을 찾는 메서드 테스트")
    void findProductsByNameTest() {
        List<Product> products = inventory.findProductsByName("콜라");

        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals("콜라", products.get(0).name());
    }

    @Test
    @DisplayName("상품의 인덱스를 찾는 메서드 테스트")
    void findIndexByProductTest() {
        Product product = inventory.inventory().get(1);
        int index = inventory.findIndexByProduct(product);

        assertEquals(1, index);
    }
}
