package com.acme.homeloans;

import com.acme.homeloans.model.Submission;
import com.acme.homeloans.model.SubmissionResponse;
import com.acme.homeloans.repository.DecisionRepository;
import com.acme.homeloans.repository.SubmissionRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Submission integration test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
public class SubmissionIT {

    @Value("${local.server.port}")
    int port;

    private RestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    DecisionRepository decisionRepository;

    @Autowired
    SubmissionRepository submissionRepository;

    @Test
    public void testSubmit() {
        Submission submission = new Submission();
        submission.setCreditScore(10);
        submission.setLoanAmount(110);
        submission.setPrice(100);
        submission.setSalary(100);

        ResponseEntity<SubmissionResponse> responseEntity = restTemplate.postForEntity("http://localhost:" + port, submission, SubmissionResponse.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody().isAccepted(), is(true));

        assertThat(decisionRepository.count(), is(1L));
        assertThat(submissionRepository.count(), is(1L));
    }
}
