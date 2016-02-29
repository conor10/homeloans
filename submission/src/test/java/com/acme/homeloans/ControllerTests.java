package com.acme.homeloans;

import com.acme.homeloans.model.Decision;
import com.acme.homeloans.model.Submission;
import com.acme.homeloans.model.SubmissionResponse;
import com.acme.homeloans.repository.DecisionRepository;
import com.acme.homeloans.repository.SubmissionRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Controller unit tests.
 */
@RunWith(MockitoJUnitRunner.class)
public class ControllerTests {

    @Mock
    DecisionRepository decisionRepository;

    @Mock
    SubmissionRepository submissionRepository;

    @InjectMocks
    private Controller controller;

    @Test
    public void testDecision() {
        Submission submission = getValidDecision();

        Decision savedDecision = new Decision();
        savedDecision.setId(1L);
        savedDecision.setSubmission(submission);

        when(decisionRepository.save(any(Decision.class))).thenReturn(savedDecision);

        SubmissionResponse submissionResponse = controller.submit(submission);
        assertThat(submissionResponse.getSubmissionId(), is(1L));

        verify(submissionRepository).save(submission);
        verify(decisionRepository).save(any(Decision.class));
    }

    @Test
    public void testMakeValidDecision() {
        Decision decision = controller.makeDecision(getValidDecision());
        assertThat(decision.isAccepted(), is(true));
    }

    @Test
    public void testMakeInvalidDecision() {
        Submission submission = new Submission();
        submission.setCreditScore(0);
        submission.setLoanAmount(110);
        submission.setPrice(100);
        submission.setSalary(1);

        Decision decision = controller.makeDecision(submission);
        assertThat(decision.isAccepted(), is(false));
    }

    private static Submission getValidDecision() {
        Submission submission = new Submission();
        submission.setCreditScore(10);
        submission.setLoanAmount(110);
        submission.setPrice(100);
        submission.setSalary(100);
        return submission;
    }
}
