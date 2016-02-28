package com.acme.homeloans;

import com.acme.homeloans.model.Decision;
import com.acme.homeloans.model.Submission;
import com.acme.homeloans.model.SubmissionResponse;
import com.acme.homeloans.repository.DecisionRepository;
import com.acme.homeloans.repository.SubmissionRepository;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
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
    DecisionRepository decisionRepository;

    @Mock
    SubmissionRepository submissionRepository;

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

        Submission submission = getValidSubmission();
        Decision savedDecision = new Decision();
        savedDecision.setId(1L);
        savedDecision.setSubmission(submission);

        when(decisionRepository.save(any(Decision.class))).thenReturn(savedDecision);

        ObjectMapper mapper = new ObjectMapper();
        byte[] content = mapper.writeValueAsBytes(submission);


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andReturn();

        SubmissionResponse submissionResponse = mapper.readValue(
                mvcResult.getResponse().getContentAsString(), SubmissionResponse.class);
        assertThat(submissionResponse.getSubmissionId(), is(1L));
    }

    private static Submission getValidSubmission() {
        Submission submission = new Submission();
        submission.setCreditScore(10);
        submission.setLoanAmount(110);
        submission.setPrice(100);
        submission.setSalary(100);
        return submission;
    }
}
