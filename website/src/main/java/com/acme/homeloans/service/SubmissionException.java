package com.acme.homeloans.service;

/**
 * Credit score exception wrapper.
 */
public class SubmissionException extends Exception {

    public SubmissionException(String message) {
        super(message);
    }

    public SubmissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
