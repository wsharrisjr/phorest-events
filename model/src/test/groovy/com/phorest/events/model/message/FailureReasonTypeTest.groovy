package com.phorest.events.model.message

import spock.lang.Specification
import spock.lang.Unroll

import static com.phorest.events.model.message.FailureReasonType.DESTINATION_PERMANENTLY_BLOCKED
import static com.phorest.events.model.message.FailureReasonType.DESTINATION_PERMANENTLY_UNREACHABLE
import static com.phorest.events.model.message.FailureReasonType.DESTINATION_TEMPORAIRLY_UNREACHABLE
import static com.phorest.events.model.message.FailureReasonType.INTERNAL_SYSTEM_ERROR
import static com.phorest.events.model.message.FailureReasonType.MESSAGE_CONTENT_INVALID
import static com.phorest.events.model.message.FailureReasonType.MESSAGE_SPAM
import static com.phorest.events.model.message.FailureReasonType.MESSAGE_TOO_LARGE
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
        DESTINATION_PERMANENTLY_UNREACHABLE | true
        DESTINATION_TEMPORAIRLY_UNREACHABLE | false
        INTERNAL_SYSTEM_ERROR               | false
        MESSAGE_CONTENT_INVALID             | false
        MESSAGE_SPAM                        | false
        MESSAGE_TOO_LARGE                   | false
        UNDETERMINED_PERMANENT_ERROR        | true
        UNDETERMINED_TEMPORARY_ERROR        | false
    }
}