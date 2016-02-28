package com.acme.homeloans.service;

/**
 * Credit score exception wrapper.
 */
public class CreditScoreException extends Exception {

    public CreditScoreException(String message) {
        super(message);
    }

    public CreditScoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
