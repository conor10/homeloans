package com.acme.homeloans.service;

import com.acme.homeloans.model.Submission;
import com.acme.homeloans.model.SubmissionResponse;

import java.util.concurrent.Future;

/**
 * Application submission service.
 */
public interface SubmissionService {
    public Future<SubmissionResponse> submit(Submission submission);
}
