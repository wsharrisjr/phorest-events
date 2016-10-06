package com.phorest.events.model.message;

import java.util.HashSet;
import java.util.Set;

public enum FailureReasonType {
    DESTINATION_PERMANENTLY_BLOCKED,
    DESTINATION_PERMANENTLY_UNREACHABLE,
    DESTINATION_TEMPORAIRLY_UNREACHABLE,
    INTERNAL_SYSTEM_ERROR,
    MESSAGE_CONTENT_INVALID,
    MESSAGE_SPAM,
    MESSAGE_TOO_LARGE,
    UNDETERMINED_PERMANENT_ERROR,
    UNDETERMINED_TEMPORARY_ERROR;

    private static final Set<FailureReasonType> PERMANENT_FAILURES = new HashSet<>();

    static {
        PERMANENT_FAILURES.add(DESTINATION_PERMANENTLY_BLOCKED);
        PERMANENT_FAILURES.add(DESTINATION_PERMANENTLY_UNREACHABLE);
        PERMANENT_FAILURES.add(UNDETERMINED_PERMANENT_ERROR);
    }

    public boolean isPermanentFailure() {
        return PERMANENT_FAILURES.contains(this);
    }
}
