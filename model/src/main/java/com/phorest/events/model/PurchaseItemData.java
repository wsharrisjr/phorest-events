package com.phorest.events.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "itemType", defaultImpl = PurchaseItemData.class, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = VoucherPurchaseItemData.class, name = "VOUCHER"),
        @JsonSubTypes.Type(value = ProductPurchaseItemData.class, name = "PRODUCT"),
})
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseItemData {
    private String id;
    private ItemType itemType;
    private String purchaseType;
    private TotalPriceData totalPriceData;
    private UnitPriceData unitPriceData;
    private BigDecimal quantity;
    private String name;

    @JsonIgnore
    public boolean isService() {
        return itemType.isService();
    }

    @JsonIgnore
    public boolean isProduct() {
        return itemType.isProduct();
    }

    @JsonIgnore
    public boolean isCourseSession() {
        return itemType.isCourseSession();
    }

    @JsonIgnore
    public BigDecimal getNetUnitPrice() {
        return unitPriceData.getNetPrice();
    }
}
