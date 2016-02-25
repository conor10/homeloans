package com.acme.homeloans;

import com.acme.homeloans.models.Borrower;
import com.acme.homeloans.models.CreditScore;
import org.junit.Test;
import org.junit.runner.RunWith;
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
 * Credit check integration test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
public class CreditCheckIT {

    @Value("${local.server.port}")
    int port;

    private RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testSubmitCreditScore() {
        Borrower borrower = new Borrower();
        borrower.setEmail("test@example.com");
        borrower.setSalary(20000.0);

        ResponseEntity<CreditScore> responseEntity = restTemplate.postForEntity("http://localhost:" + port, borrower, CreditScore.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody().getScore(), is(10));
    }
}
