package com.phorest.events.model.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.phorest.events.model.EventData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class MessageDeliveryFeedbackData implements EventData {
    private String messageId;
    private String bundleId;
    private String providedMessageId;
    private DeliveryStatus deliveryStatus;
    private MessageFailureData failure;
    private DateTime timestamp;

    @JsonIgnore
    public FailureReasonType getFailureReasonType() {
        return failure.getType();
    }

    @JsonIgnore
    public String getFailureDescription() {
        return failure.getDescription();
    }

}
