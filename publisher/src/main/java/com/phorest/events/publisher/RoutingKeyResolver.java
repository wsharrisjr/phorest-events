package com.phorest.events.publisher;

import com.phorest.events.model.EventSource;

public final class RoutingKeyResolver {

    private RoutingKeyResolver() {
    }

    public static String resolve(String routingKeyTemplate, EventSource eventSource) {
        return String.format(routingKeyTemplate, eventSource.getRoutingKeyArgs());
    }
}
