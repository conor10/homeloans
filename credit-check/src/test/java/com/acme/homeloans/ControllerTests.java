package com.acme.homeloans;

import com.acme.homeloans.models.Borrower;
import com.acme.homeloans.models.CreditScore;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Controller unit tests.
 */
public class ControllerTests {

    private Controller controller = new Controller();

    @Test
    public void testSubmitGoodBorrower() {
        Borrower borrower = new Borrower();
        borrower.setEmail("test@example.com");
        borrower.setSalary(20000.0);

        CreditScore creditScore = controller.submit(borrower);

        assertThat(creditScore.getScore(), is(10));
    }

    @Test
    public void testSubmitBadBorrower() {
        Borrower borrower = new Borrower();
        borrower.setEmail("test@gmail.com");
        borrower.setSalary(10000.0);

        CreditScore creditScore = controller.submit(borrower);

        assertThat(creditScore.getScore(), is(1));
    }
}
