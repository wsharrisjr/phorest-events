package com.phorest.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class PaymentData {
    private BigDecimal amount;
    private String type;
    private SupplementalPaymentTypeData supplementalPaymentType;
    private String serialNumber;
    private VoucherData voucherData;
}
