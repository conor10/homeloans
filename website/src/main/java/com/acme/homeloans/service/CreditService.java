package com.acme.homeloans.service;

import com.acme.homeloans.model.Borrower;
import com.acme.homeloans.model.CreditScore;

import java.util.concurrent.Future;

/**
 * Borrower credit service.
 */
public interface CreditService {
    public Future<CreditScore> getCreditScore(Borrower borrower);
}
