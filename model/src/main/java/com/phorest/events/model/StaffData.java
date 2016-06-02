package com.phorest.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class StaffData {
    private String id;
    private String firstName;
    private String lastName;
}
