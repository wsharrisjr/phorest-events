package com.phorest.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class TotalPriceData {
    private BigDecimal taxAmount;
    private BigDecimal netAmount;
    private BigDecimal grossAmount;
}
