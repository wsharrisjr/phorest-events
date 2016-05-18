package com.phorest.events.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentData {
  private BigDecimal amount;
  private String type;
}
