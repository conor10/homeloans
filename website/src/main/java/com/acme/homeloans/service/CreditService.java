package com.acme.homeloans.service;

import com.acme.homeloans.model.Borrower;
import com.acme.homeloans.model.CreditScore;
import com.acme.homeloans.WebsiteSettings;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Credit check service plumbing.
 */
@Service
public class CreditService {

    @Autowired
    private WebsiteSettings websiteSettings;

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getDefaultCreditScore")
    public CreditScore getCreditScore(Borrower borrower) throws CreditScoreException {
        ResponseEntity<CreditScore> responseEntity = restTemplate.postForEntity(
                websiteSettings.getCreditCheck().getUrl(),
                borrower, CreditScore.class);

        HttpStatus httpStatus = responseEntity.getStatusCode();
        if (httpStatus != HttpStatus.OK) {
            throw new CreditScoreException("Unable to calculate credit score, received response: " +
                    "[" + httpStatus.value() + "] " + httpStatus.getReasonPhrase());
        }
        return responseEntity.getBody();
    }

    public CreditScore getDefaultCreditScore(Borrower borrower) {
        CreditScore creditScore = new CreditScore();
        creditScore.setScore(5);
        return creditScore;
    }
}





