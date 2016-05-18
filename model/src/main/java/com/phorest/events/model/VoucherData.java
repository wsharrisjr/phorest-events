package com.phorest.events.model;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;

@Data
public class VoucherData {
  private String id;
  private DateTime expiryDate;
  private DateTime issueDate;
  private BigDecimal discountedOriginalBalance;
  private BigDecimal originalBalance;
  private String serial;
  private VoucherDiscountData voucherDiscount;

}
