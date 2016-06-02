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
    public ProductPurchaseItemData(String id, ItemType itemType, String purchaseType, BigDecimal quantity, String name, ProductCategoryData productCategory) {
        super(id, itemType, purchaseType, quantity, name);
        this.productCategory = productCategory;
    }

}
