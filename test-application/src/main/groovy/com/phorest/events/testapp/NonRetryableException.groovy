package com.phorest.events.testapp

class NonRetryableException extends RuntimeException {

    NonRetryableException(String m) {
        super(m)
    }
}
