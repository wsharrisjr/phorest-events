package com.phorest.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.math.BigDecimal;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class VoucherData {
    private String id;
    private DateTime expiryDate;
    private DateTime issueDate;
    private BigDecimal discountedOriginalBalance;
    private BigDecimal originalBalance;
    private BigDecimal remainingBalance;
    private String serial;
    private VoucherDiscountData voucherDiscount;

}
