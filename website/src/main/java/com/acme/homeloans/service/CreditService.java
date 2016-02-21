package com.acme.homeloans.service;

import com.acme.homeloans.model.Borrower;
import com.acme.homeloans.model.CreditScore;
import com.acme.homeloans.website.WebsiteSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

/**
 * Credit check service plumbing.
 */
@Service
public class CreditService {

    @Autowired
    private WebsiteSettings websiteSettings;

    @Autowired
    private RestTemplate restTemplate;

    @Async
    public Future<CreditScore> getCreditScore(Borrower borrower) {

        ResponseEntity<CreditScore> responseEntity = restTemplate.postForEntity(websiteSettings.getSubmission().getUrl(),
                borrower, CreditScore.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            // error
        }
        CreditScore creditScore = responseEntity.getBody();
        return new AsyncResult<>(creditScore);
    }
}
