package com.phorest.events.model.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.phorest.events.model.ItemType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum FailureReasonType {
    DESTINATION_PERMANENTLY_BLOCKED,
    DESTINATION_TEMPORARILY_UNREACHABLE,
    INBOX_FULL,
    INTERNAL_SYSTEM_ERROR,
    MESSAGE_CONTENT_INVALID,
    MESSAGE_SPAM,
    MESSAGE_TOO_LARGE,
    PERMANENTLY_BLOCKED_BY_PROVIDER,
    PROVIDER_TEMPORARILY_UNREACHABLE,
    TEMPORARILY_BLOCKED_BY_PROVIDER,
    UNDETERMINED_PERMANENT_ERROR,
    UNDETERMINED_TEMPORARY_ERROR;

    private static final Set<FailureReasonType> PERMANENT_FAILURES = new HashSet<>();

    static {
        PERMANENT_FAILURES.add(DESTINATION_PERMANENTLY_BLOCKED);
        PERMANENT_FAILURES.add(MESSAGE_SPAM);
        PERMANENT_FAILURES.add(PERMANENTLY_BLOCKED_BY_PROVIDER);
        PERMANENT_FAILURES.add(UNDETERMINED_PERMANENT_ERROR);
    }

    private static Map<String, FailureReasonType> failureReasonTypeMapping;

    static {
        failureReasonTypeMapping = Arrays.stream(FailureReasonType.values())
                .collect(Collectors.toMap(FailureReasonType::name, Function.identity()));
    }

    @JsonCreator
    public static FailureReasonType fromString(String failureReasonName) {
        return failureReasonTypeMapping.getOrDefault(failureReasonName, null);
    }

    public boolean isPermanentFailure() {
        return PERMANENT_FAILURES.contains(this);
    }

    public boolean isPermanentlyBlockedByUser() {
        return this == DESTINATION_PERMANENTLY_BLOCKED;
    }
}
