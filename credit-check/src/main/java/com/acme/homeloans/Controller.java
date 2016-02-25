package com.acme.homeloans;

import com.acme.homeloans.models.Borrower;
import com.acme.homeloans.models.CreditScore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Credit check controller.
 *
 */
@RestController
public class Controller {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    private static final List<String> BAD_DOMAINS = Arrays.asList(
            "gmail.com", "hotmail.com", "yahoo.com"
    );

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public CreditScore submit(@RequestBody Borrower borrower) {
        return getCreditScore(borrower);
    }

    private CreditScore getCreditScore(Borrower borrower) {

        String email = borrower.getEmail();

        int score = 10;
        if (BAD_DOMAINS.stream().filter(p -> email.contains(p)).count() != 0) {
            score -= 5;
        }

        if (borrower.getSalary() < 20000.0) {
            score -= 4;
        }

        CreditScore creditScore = new CreditScore();
        creditScore.setScore(Math.max(0, score));

        log.info("Credit score calculated: {}", creditScore);

        return creditScore;
    }
}
