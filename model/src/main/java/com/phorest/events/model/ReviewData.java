package com.phorest.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ReviewData implements  EventData {
    private Long id;
    private String type;
    private String author;
    private int rating;
    private String businessId;
    private String branchId;
    private String providedId;
    private ZonedDateTime providedCreationDate;

}
