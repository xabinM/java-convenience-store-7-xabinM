package store.validator;

import store.exception.Exception;

public class YNConfirmValidator {
    public static void validateInputYN(String input) {
        if (!input.equals("Y") && !input.equals("N")) {
            throw new IllegalArgumentException(Exception.INVALID_INPUT.getMessage());
        }
    }
}
