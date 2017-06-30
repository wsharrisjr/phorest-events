package com.phorest.events.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event<T> {
    @NotNull
    private String eventId;
    @NotNull
    private DateTime timestamp;
    @Deprecated
    private String type;

    /**
     * Use {@link #payload} instead of it
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
    @Valid
    @Deprecated
    private T data;
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type", visible = true)
    @Valid
    private T payload;

    /**
     * Use {@link #create} instead of it
     */
    @Deprecated
    public static <T extends EventData> Event<T> createNew(String type, T data) {
        return new Event<>(UUID.randomUUID().toString(), DateTime.now(), type, data, null);
    }

    public static <T> Event<T> create(T payload) {
        return new Event<>(UUID.randomUUID().toString(), DateTime.now(), null, null, payload);
    }
}