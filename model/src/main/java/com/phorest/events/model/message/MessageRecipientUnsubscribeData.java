package com.phorest.events.model.message;

import com.phorest.events.model.EventData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class MessageRecipientUnsubscribeData implements EventData {
    private String providedMessageId;
    private DateTime timestamp;
    private String address;

}
