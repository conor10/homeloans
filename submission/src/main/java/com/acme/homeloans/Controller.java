package com.acme.homeloans;

import com.acme.homeloans.model.Decision;
import com.acme.homeloans.model.Submission;
import com.acme.homeloans.model.SubmissionResponse;
import com.acme.homeloans.repository.DecisionRepository;
import com.acme.homeloans.repository.SubmissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Submission controller.
 */
@RestController
public class Controller {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private DecisionRepository decisionRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public SubmissionResponse submit(@RequestBody Submission submission) {
        Decision decision = makeDecision(submission);
        Decision savedDecision = save(submission, decision);

        log.info("Decision made: {}", savedDecision);

        return new SubmissionResponse(
                savedDecision.getId(), savedDecision.isAccepted(), savedDecision.getMessage());
    }

    Decision makeDecision(Submission submission) {

        List<String> message = new ArrayList<>(3);
        boolean accepted = true;

        if (submission.getLoanAmount() / submission.getPrice() > 1.5) {
            accepted = false;
            message.add("[LTV] We thought 150% LTV was generous");
        }

        if (submission.getCreditScore() < 1) {
            accepted = false;
            message.add("[Bad credit] A credit score less then 1? Seriously???");
        }

        if (submission.getSalary() * 100. < submission.getLoanAmount()) {
            accepted = false;
            message.add("[Low salary] What are you buying? A penthouse?");
        }

        Decision decision = new Decision();
        decision.setAccepted(accepted);
        decision.setMessage(formatMessage(message));
        return decision;
    }

    private String formatMessage(List<String> message) {
        return message.stream().reduce(
                "Unmet criteria (if any):", (a, b) -> a + "\n" + b);
    }

    private Decision save(Submission submission, Decision decision) {
        Submission savedSubmission = submissionRepository.save(submission);
        decision.setSubmission(savedSubmission);
        return decisionRepository.save(decision);
    }
}
