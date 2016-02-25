package com.acme.homeloans.website;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Website configuration
 */
@Component
@ConfigurationProperties(prefix="website")
public class WebsiteSettings {

    public WebsiteSettings() { }

    private CreditCheck creditCheck;
    private Submission submission;

    public CreditCheck getCreditCheck() {
        return creditCheck;
    }

    public void setCreditCheck(CreditCheck creditCheck) {
        this.creditCheck = creditCheck;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public static class CreditCheck {
        public CreditCheck() { }

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Submission {
        public Submission() { }

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
