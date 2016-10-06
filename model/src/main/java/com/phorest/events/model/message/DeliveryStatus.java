package com.phorest.events.model.message;

public enum DeliveryStatus {
    DELIVERED,
    SUBMITTED,
    FAILED,
    UNKNOWN;

    public boolean isFailed() {
        return this == FAILED;
    }
}
