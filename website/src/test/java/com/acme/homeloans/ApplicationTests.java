package com.acme.homeloans;

import com.acme.homeloans.model.Borrower;
import com.acme.homeloans.model.CreditScore;
import com.acme.homeloans.model.Submission;
import com.acme.homeloans.model.SubmissionResponse;
import com.acme.homeloans.service.CreditService;
import com.acme.homeloans.service.SubmissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Application unit tests.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class ApplicationTests {

    @Mock
    private SubmissionService submissionService;

    @Mock
    private CreditService creditService;

    @InjectMocks
    private Controller controller;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testSubmit() throws Exception {
        Borrower borrower = new Borrower();
        borrower.setEmail("test@example.com");
        borrower.setSalary(20000.0);

        CreditScore creditScore = new CreditScore();
        creditScore.setScore(10);

        SubmissionResponse submissionResponse = new SubmissionResponse();
        submissionResponse.setAccepted(true);

        when(creditService.getCreditScore(any(Borrower.class))).thenReturn(creditScore);
        when(submissionService.submit(any(Submission.class))).thenReturn(new AsyncResult<>(submissionResponse));

        ObjectMapper mapper = new ObjectMapper();
        byte[] content = mapper.writeValueAsBytes(borrower);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content(content))
                .andExpect(status().isOk())
                .andReturn();

        SubmissionResponse response = mapper.readValue(
                mvcResult.getResponse().getContentAsString(), SubmissionResponse.class);
        assertThat(response.isAccepted(), is(true));
    }


}
