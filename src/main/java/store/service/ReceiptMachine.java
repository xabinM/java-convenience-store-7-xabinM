package store.service;

import store.model.Receipt;
import store.model.ResultDTO;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ReceiptMachine {
    private final boolean memberShip;

    private final List<String> receipt = new ArrayList<>();
    DecimalFormat priceFormat = new DecimalFormat("#,###");

    public ReceiptMachine(boolean memberShip) {
        this.memberShip = memberShip;
    }

    public List<String> makeReceipt(List<ResultDTO> infos) {
        makeProductPart(infos);
        makeFreeGiftPart(infos);
        makeTotalPart(infos);

        return receipt;
    }

    private void makeProductPart(List<ResultDTO> infos) {
        receipt.add(Receipt.HISTORY_START_BAR.getOutput());
        receipt.add(Receipt.ITEM_HISTORY.getOutput());
        for (ResultDTO info : infos) {
            int totalPrice = info.quantity() * info.price();
            receipt.add(info.name() + "\t\t\t" + info.quantity() + "\t" + priceFormat.format(totalPrice));
        }
    }

    private void makeFreeGiftPart(List<ResultDTO> infos) {
        receipt.add(Receipt.FREE_GIFT_BAR.getOutput());
        for (ResultDTO info : infos) {
            if (info.promotion()) {
                int freeGiftAmount = getFreeGiftAmount(info);
                receipt.add(info.name() + "\t\t\t" + freeGiftAmount);
            }
        }
    }

    private int getFreeGiftAmount(ResultDTO info) {
        int quotient = info.quantity() / (info.buy() + info.get());
        if (info.quantity() > info.preStock()) {
            quotient = info.preStock() / (info.buy() + info.get());
        }
        return info.get() * quotient;
    }

    private void makeTotalPart(List<ResultDTO> infos) {
        receipt.add(Receipt.HISTORY_LAST_BAR.getOutput());
        makeTotalPrice(infos);
    }

    private void makeTotalPrice(List<ResultDTO> infos) {
        int totalAmount = infos.stream()
                .mapToInt(ResultDTO::quantity)
                .sum();
        int totalPrice = infos.stream()
                .mapToInt(resultDTO -> resultDTO.quantity() * resultDTO.price())
                .sum();

        receipt.add(Receipt.TOTAL_PURCHASE.getOutput()
                + "\t\t" + totalAmount + "\t" + priceFormat.format(totalPrice));
        makePromotionDiscount(infos, totalPrice);
    }

    private void makePromotionDiscount(List<ResultDTO> infos, int totalPrice) {
        int promotionDiscount = 0;
        for (ResultDTO info : infos) {
            if (info.promotion()) {
                int freeGiftAmount = getFreeGiftAmount(info);
                promotionDiscount += (freeGiftAmount) * info.price();
            }
        }
        receipt.add(Receipt.EVENT_DISCOUNT.getOutput() + "\t\t\t" + priceFormat.format(-promotionDiscount));
        makeMembershipDiscount(infos, totalPrice, promotionDiscount);
    }

    private void makeMembershipDiscount(List<ResultDTO> infos, int totalPrice, int promotionDiscount) {
        int membershipDiscount = 0;
        if (memberShip) {
            membershipDiscount = MemberShipService.discountByMembership(infos.stream()
                    .filter(resultDTO -> !resultDTO.promotion())
                    .mapToInt(resultDTO -> resultDTO.quantity() * resultDTO.price())
                    .sum());
        }
        receipt.add(Receipt.MEMBERSHIP_DISCOUNT.getOutput() +
                "\t\t" + priceFormat.format(-MemberShipService.checkDiscountMaximum(membershipDiscount)));
        makeFinalPayment(totalPrice, promotionDiscount, MemberShipService.checkDiscountMaximum(membershipDiscount));
    }

    private void makeFinalPayment(int totalPrice, int promotionDiscount, int membershipDiscount) {
        int finalPayment = totalPrice - promotionDiscount - membershipDiscount;
        receipt.add(Receipt.FINAL_AMOUNT.getOutput() + " \t\t\t" + priceFormat.format(finalPayment));
    }
}
