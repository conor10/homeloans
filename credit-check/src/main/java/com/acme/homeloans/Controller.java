package com.acme.homeloans;

import com.acme.homeloans.models.Borrower;
import com.acme.homeloans.models.CreditScore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;

/**
 * Credit check controller.
 */
public class Controller {

    private static final List<String> BAD_DOMAINS = Arrays.asList(
            "gmail.com", "hotmail.com", "yahoo.com"
    );

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public CreditScore submit(Borrower borrower) {
        return getCreditScore(borrower);
    }

    private CreditScore getCreditScore(Borrower borrower) {

        String email = borrower.getEmail();

        int score = 10;
        if (BAD_DOMAINS.stream().filter(p -> email.contains(p)).count() != 0) {
            score =- 5;
        }

        if (borrower.getSalary() < 20000.0) {
            score =- 4;
        }

        CreditScore creditScore = new CreditScore();
        creditScore.setScore(Math.max(0, score));
        return creditScore;
    }
}
