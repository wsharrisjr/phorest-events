package com.phorest.events.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "itemType", defaultImpl = PurchaseItemData.class, visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = VoucherPurchaseItemData.class, name = "VOUCHER"),
  @JsonSubTypes.Type(value = ProductPurchaseItemData.class, name = "PRODUCT"),
})
public class PurchaseItemData {
  protected String id;
  protected ItemType itemType;
  protected String purchaseType;
  protected BigDecimal quantity;
  protected String name;

  @JsonIgnore
  public boolean isService() {
    return itemType.isService();
  }
}
