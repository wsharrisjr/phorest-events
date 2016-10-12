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
    private String providedMessageId;
    private DeliveryStatus deliveryStatus;
    private MessageFailureData failure;
    private DateTime timestamp;

    @JsonIgnore
    public boolean isFailed() {
        return deliveryStatus.isFailed();
    }

    @JsonIgnore
    public boolean isPermanentFailure() {
        return isFailed() && failure.isPermanentFailure();
    }

}
