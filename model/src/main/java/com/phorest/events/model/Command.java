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
public class Command<T> {
    @NotNull
    private String commandId;
    @NotNull
    private DateTime timestamp;
    @Valid
    @NotNull
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type", visible = true)
    private T payload;

    public static <T> Command<T> create(T payload) {
        return new Command<>(UUID.randomUUID().toString(), DateTime.now(), payload);
    }
}