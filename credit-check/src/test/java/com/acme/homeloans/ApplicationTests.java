package com.acme.homeloans;

import com.acme.homeloans.models.Borrower;

import com.acme.homeloans.models.CreditScore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Application unit tests.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class ApplicationTests {

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new Controller()).build();
    }

    @Test
    public void testSubmit() throws Exception {
        Borrower borrower = new Borrower();
        borrower.setEmail("test@example.com");
        borrower.setSalary(20000.0);

        ObjectMapper mapper = new ObjectMapper();
        byte[] content = mapper.writeValueAsBytes(borrower);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andReturn();

        CreditScore creditScore = mapper.readValue(
                mvcResult.getResponse().getContentAsString(), CreditScore.class);
        assertThat(creditScore.getScore(), is(10));
    }
}
