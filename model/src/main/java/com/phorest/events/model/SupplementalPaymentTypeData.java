package com.phorest.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class SupplementalPaymentTypeData {

    private String id;
    private String name;
    private String description;
    private String code;
    private String mode;
}
