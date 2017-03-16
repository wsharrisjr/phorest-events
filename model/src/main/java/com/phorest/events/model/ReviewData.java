package com.phorest.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

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
    private DateTime providedCreationDate;

}
