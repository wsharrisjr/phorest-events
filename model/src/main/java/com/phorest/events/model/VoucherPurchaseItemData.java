package com.phorest.events.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class VoucherPurchaseItemData extends PurchaseItemData {
    private VoucherData voucherData;

    @Builder
    public VoucherPurchaseItemData(String id,
                                   ItemType itemType,
                                   String purchaseType,
                                   TotalPriceData totalPriceData,
                                   UnitPriceData unitPriceData,
                                   DiscountData discountData,
                                   BigDecimal quantity,
                                   String name,
                                   VoucherData voucherData) {
        super(id, itemType, purchaseType, totalPriceData, unitPriceData, discountData, quantity, name);
        this.voucherData = voucherData;
    }

}
