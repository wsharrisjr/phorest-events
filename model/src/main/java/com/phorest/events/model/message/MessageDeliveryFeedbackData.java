package com.phorest.events.model.message;

import com.phorest.events.model.EventData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class MessageDeliveryFeedbackData implements EventData {
    private String messageId;
    private DeliveryStatus deliveryStatus;
    private MessageFailureData failure;

}
