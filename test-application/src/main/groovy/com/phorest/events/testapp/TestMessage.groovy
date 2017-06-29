package com.phorest.events.testapp

import groovy.transform.Canonical
import org.hibernate.validator.constraints.NotEmpty

@Canonical
class TestMessage {
    @NotEmpty
    String text
}
