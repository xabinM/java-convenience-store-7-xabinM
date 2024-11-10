package store.service;

public class MemberShipService {
    public static int discountByMembership(int price) {

        return (int) (price * 0.3);
    }

    public static int checkDiscountMaximum(int discountAmount) {

        return Math.min(discountAmount, 8000);
    }
}

