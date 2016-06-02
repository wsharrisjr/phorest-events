package com.phorest.events.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface EventSource {

    Object[] NONE = new Object[0];

    @JsonIgnore
    default Object[] getRoutingKeyArgs() {
        return NONE;
    }
}
