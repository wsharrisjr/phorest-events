package com.phorest.events.model;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PurchaseData {
  private String branchId;
  private String businessId;
  private ClientData client;
  private String description;
  private BigDecimal onlineDeposit;
  private BigDecimal onlineDiscount;
  private List<PaymentData> payments;
  private DateTime purchaseDateTime;
  private List<PurchaseItemData> purchaseItems;
  private StaffData staff;
  private String transactionId;
  private VoucherDiscountData voucherDiscount;
}
