package com.acme.homeloans.service;

import com.acme.homeloans.model.Submission;
import com.acme.homeloans.model.SubmissionResponse;
import com.acme.homeloans.WebsiteSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

/**
 * Risk check service plumbing.
 */
@Service
public class SubmissionService {

    @Autowired
    private WebsiteSettings websiteSettings;

    @Autowired
    private RestTemplate restTemplate;

    @Async
    public Future<SubmissionResponse> submit(Submission submission) throws SubmissionException {
        ResponseEntity<SubmissionResponse> responseEntity = restTemplate.postForEntity(websiteSettings.getSubmission().getUrl(),
                submission, SubmissionResponse.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new SubmissionException("Unable to submit application, received response: " +
                "[" + httpStatus.value() + "] " + httpStatus.getReasonPhrase());
        }
        SubmissionResponse submissionResponse = responseEntity.getBody();
        return new AsyncResult<>(submissionResponse);
    }
}
