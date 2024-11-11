package store.validator;

import store.model.Inventory;
import store.model.InventoryLoader;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


public class WishProductValidatorTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory(new InventoryLoader().loadProductsFromFile());
    }

    @Test
    @DisplayName("상품 이름이 유효한지 검증하는 테스트")
    void validateNameTest() {
        assertDoesNotThrow(() -> WishProductValidator.validateName("콜라", inventory));
        assertThrows(IllegalArgumentException.class, () -> WishProductValidator.validateName("술", inventory));
    }

    @Test
    @DisplayName("수량이 유효한지 검증하는 테스트")
    void validateQuantityTest() {
        assertDoesNotThrow(() -> WishProductValidator.validateQuantity("콜라", 5, inventory));
        assertThrows(IllegalArgumentException.class, () -> WishProductValidator.validateQuantity("콜라", 25, inventory));
    }

    @Test
    @DisplayName("상품 이름과 수량을 한 번에 검증하는 테스트")
    void validateWishProductTest() {
        assertDoesNotThrow(() -> WishProductValidator.validateWishProduct("콜라", 5, inventory));
        assertThrows(IllegalArgumentException.class, () -> WishProductValidator.validateWishProduct("술", 5, inventory));
        assertThrows(IllegalArgumentException.class, () -> WishProductValidator.validateWishProduct("콜라", 25, inventory));
    }

    @Test
    @DisplayName("입력 포맷이 유효한지 검증하는 테스트")
    void validateInputFormatTest() {
        assertDoesNotThrow(() -> WishProductValidator.validateInputFormat("[콜라-5]"));
        assertThrows(IllegalArgumentException.class, () -> WishProductValidator.validateInputFormat("콜라-5"));
        assertThrows(IllegalArgumentException.class, () -> WishProductValidator.validateInputFormat("[콜라5]"));
        assertThrows(IllegalArgumentException.class, () -> WishProductValidator.validateInputFormat("[콜라-]"));
    }

    @Test
    @DisplayName("수량을 Integer로 파싱할 수 있는지 검증하는 테스트")
    void validateParseIntegerTest() {
        assertDoesNotThrow(() -> WishProductValidator.validateParseInteger("5"));
        assertThrows(IllegalArgumentException.class, () -> WishProductValidator.validateParseInteger("abc"));
        assertThrows(IllegalArgumentException.class, () -> WishProductValidator.validateParseInteger("-5"));
        assertThrows(IllegalArgumentException.class, () -> WishProductValidator.validateParseInteger("0"));
    }
}
