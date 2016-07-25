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
public class ProductPurchaseItemData extends PurchaseItemData {
    private ProductCategoryData productCategory;

    @Builder
    public ProductPurchaseItemData(String id,
                                   ItemType itemType,
                                   String purchaseType,
                                   TotalPriceData totalPriceData,
                                   UnitPriceData unitPriceData,
                                   DiscountData discountData,
                                   BigDecimal quantity,
                                   String name,
                                   ProductCategoryData productCategory,
                                   String groupBookingId,
                                   StaffData staff) {
        super(id, itemType, purchaseType, totalPriceData, unitPriceData, discountData, quantity, name, groupBookingId, staff);
        this.productCategory = productCategory;
    }

}
