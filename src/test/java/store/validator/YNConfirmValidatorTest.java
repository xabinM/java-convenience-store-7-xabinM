package store.validator;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class YNConfirmValidatorTest {

    @Test
    @DisplayName("입력 값이 'Y' 또는 'N' 일 때 예외가 발생하지 않아야 한다.")
    void validateInputYNTest_ValidInput() {
        assertDoesNotThrow(() -> YNConfirmValidator.validateInputYN("Y"));
        assertDoesNotThrow(() -> YNConfirmValidator.validateInputYN("N"));
    }

    @Test
    @DisplayName("입력 값이 'Y' 또는 'N' 이 아닐 때 예외가 발생해야 한다.")
    void validateInputYNTest_InvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> YNConfirmValidator.validateInputYN("A"));
        assertThrows(IllegalArgumentException.class, () -> YNConfirmValidator.validateInputYN("yes"));
        assertThrows(IllegalArgumentException.class, () -> YNConfirmValidator.validateInputYN("no"));
        assertThrows(IllegalArgumentException.class, () -> YNConfirmValidator.validateInputYN(""));
    }
}
