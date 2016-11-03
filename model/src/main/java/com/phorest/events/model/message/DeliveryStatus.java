package com.phorest.events.model.message;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum DeliveryStatus {
    DELIVERED,
    SUBMITTED,
    FAILED,
    UNKNOWN;

    private static Map<String, DeliveryStatus> deliveryStatusMapping;

    static {
        deliveryStatusMapping = Arrays.stream(DeliveryStatus.values())
                .collect(Collectors.toMap(DeliveryStatus::name, Function.identity()));
    }

    @JsonCreator
    public static DeliveryStatus fromString(String deliveryStatusName) {
        return deliveryStatusMapping.getOrDefault(deliveryStatusName, UNKNOWN);
    }

}