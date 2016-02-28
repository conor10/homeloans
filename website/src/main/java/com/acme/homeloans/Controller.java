package com.acme.homeloans;

import com.acme.homeloans.model.Borrower;
import com.acme.homeloans.model.CreditScore;
import com.acme.homeloans.model.Submission;
import com.acme.homeloans.model.SubmissionResponse;
import com.acme.homeloans.service.CreditScoreException;
import com.acme.homeloans.service.CreditService;
import com.acme.homeloans.service.SubmissionService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Web controller.
 */
@RestController  // alleviates need to use @ResponseBody
public class Controller {

    private static Logger log = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private CreditService creditService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public SubmissionResponse signup(@ModelAttribute Borrower borrower) {
        SubmissionResponse submissionResponse = processApplication(borrower);

        return submissionResponse;
    }

    private SubmissionResponse processApplication(Borrower borrower) {
        CreditScore creditScore = getCreditScore(borrower);

        Submission submission = buildSubmission(borrower, creditScore);
        SubmissionResponse submissionResponse = submitApplication(submission);

        return submissionResponse;
    }


    private CreditScore getCreditScore(Borrower borrower) {
        try {
            return creditService.getCreditScore(borrower);
        } catch (CreditScoreException e) {
            // This should never be called using Hysterix
            throw new RuntimeException("Unable to process Credit Score", e);
        }
    }

    /*
     * This service is not using Hystrix to provide a comparison of how an equivalent
     * async implementation may appear.
     */
    private SubmissionResponse submitApplication(Submission submission) {
        try {
            Future<SubmissionResponse> submissionResponseFuture = submissionService.submit(submission);
            SubmissionResponse submissionResponse = submissionResponseFuture.get(5, TimeUnit.SECONDS);
            return submissionResponse;
        } catch (Throwable t) {
            String message = "There were issues processing application";
            log.warn(message, t);
            SubmissionResponse submissionResponse = new SubmissionResponse();
            submissionResponse.setAccepted(false);
            submissionResponse.setMessage(message + t.getMessage());
            return submissionResponse;
        }
    }

    private Submission buildSubmission(Borrower borrower, CreditScore creditScore) {
        Submission submission = new Submission();

        submission.setEmail(borrower.getEmail());
        submission.setLoanAmount(borrower.getLoanAmount());
        submission.setPrice(borrower.getPrice());
        submission.setSalary(borrower.getSalary());
        submission.setCreditScore(creditScore.getScore());

        return submission;
    }
}
