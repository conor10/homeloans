package com.acme.homeloans.website;

import com.acme.homeloans.model.Borrower;
import com.acme.homeloans.model.CreditScore;
import com.acme.homeloans.model.Submission;
import com.acme.homeloans.model.SubmissionResponse;
import com.acme.homeloans.service.CreditService;
import com.acme.homeloans.service.SubmissionService;
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

/**
 * Web controller.
 */
@RestController
public class LoanController {

    private static Logger log = LoggerFactory.getLogger(LoanController.class);

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
        Optional<CreditScore> creditScore = getCreditScore(borrower);
        if (!creditScore.isPresent()) {
            // error
        }

        // add timeout's to jobs & default for Optional.get()
        Submission submission = buildSubmission(borrower, creditScore.get());
        Future<SubmissionResponse> submissionResponseFuture = submissionService.submit(submission);
        Optional<SubmissionResponse> submissionResponse = Optional.empty();

        try {
            submissionResponse = Optional.of(submissionResponseFuture.get());
        } catch (InterruptedException|ExecutionException e) {
            log.warn("Error processing submission", e);
        }
        return submissionResponse.get();
    }

    private Optional<CreditScore> getCreditScore(Borrower borrower) {
        Future<CreditScore> creditScoreFuture = creditService.getCreditScore(borrower);
        Optional<CreditScore> creditScore = Optional.empty();

        try {
            creditScore = Optional.of(creditScoreFuture.get());
        } catch (InterruptedException|ExecutionException e) {
            log.warn("Credit score could not be obtained", e);
        }

        return creditScore;
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
