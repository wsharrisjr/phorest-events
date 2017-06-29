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
    private String type;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
    @Valid
    @NotNull
    private T data;

    public static <T extends EventData> Event<T> createNew(String type, T data) {
        return new Event<>(UUID.randomUUID().toString(), DateTime.now(), type, data);
    }
}