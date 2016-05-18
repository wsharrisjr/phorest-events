package com.phorest.events.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VoucherDiscountData {
  private String type;
  private BigDecimal value;
}
