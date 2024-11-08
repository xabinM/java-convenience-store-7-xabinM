package store.model;

import java.time.LocalDate;

public record Promotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {

    public boolean isApplicable(int quantity) {

        return quantity % (buy + get) == 0;
    }

    public boolean isDateInRange(LocalDate currentDate) {

        return currentDate.isAfter(startDate) && currentDate.isBefore(endDate);
    }
}
