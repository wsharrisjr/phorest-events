package com.phorest.events.model.message

import spock.lang.Specification
import spock.lang.Unroll

import static com.phorest.events.model.message.FailureReasonType.DESTINATION_PERMANENTLY_BLOCKED
import static com.phorest.events.model.message.FailureReasonType.DESTINATION_TEMPORARILY_UNREACHABLE
import static com.phorest.events.model.message.FailureReasonType.INBOX_FULL
import static com.phorest.events.model.message.FailureReasonType.INTERNAL_SYSTEM_ERROR
import static com.phorest.events.model.message.FailureReasonType.MESSAGE_CONTENT_INVALID
import static com.phorest.events.model.message.FailureReasonType.MESSAGE_SPAM
import static com.phorest.events.model.message.FailureReasonType.MESSAGE_TOO_LARGE
import static com.phorest.events.model.message.FailureReasonType.PERMANENTLY_BLOCKED_BY_PROVIDER
import static com.phorest.events.model.message.FailureReasonType.PROVIDER_TEMPORARILY_UNREACHABLE
import static com.phorest.events.model.message.FailureReasonType.TEMPORARILY_BLOCKED_BY_PROVIDER
import static com.phorest.events.model.message.FailureReasonType.UNDETERMINED_PERMANENT_ERROR
import static com.phorest.events.model.message.FailureReasonType.UNDETERMINED_TEMPORARY_ERROR

@Unroll
class FailureReasonTypeTest extends Specification {

    def "should check if failure is permanent"() {
        when:
        boolean isPermanent = failureReasonType.permanentFailure

        then:
        isPermanent == expectedIsPermanent

        where:
        failureReasonType                   | expectedIsPermanent
        DESTINATION_PERMANENTLY_BLOCKED     | true
        DESTINATION_TEMPORARILY_UNREACHABLE | false
        INBOX_FULL                          | false
        INTERNAL_SYSTEM_ERROR               | false
        MESSAGE_CONTENT_INVALID             | false
        MESSAGE_SPAM                        | true
        MESSAGE_TOO_LARGE                   | false
        PERMANENTLY_BLOCKED_BY_PROVIDER     | true
        PROVIDER_TEMPORARILY_UNREACHABLE    | false
        TEMPORARILY_BLOCKED_BY_PROVIDER     | false
        UNDETERMINED_PERMANENT_ERROR        | true
        UNDETERMINED_TEMPORARY_ERROR        | false
    }
}