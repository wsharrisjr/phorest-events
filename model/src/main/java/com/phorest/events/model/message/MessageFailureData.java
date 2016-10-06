package com.phorest.events.model.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class MessageFailureData {
    private FailureReasonType type;
    private String description;

    @JsonIgnore
    public boolean isPermanentFailure() {
        return type.isPermanentFailure();
    }
}
