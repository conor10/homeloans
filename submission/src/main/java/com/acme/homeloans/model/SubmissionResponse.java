package com.acme.homeloans.model;

/**
 * Submission response.
 */
public class SubmissionResponse {
    private boolean accepted;
    private String message;
    private long submissionId;

    public SubmissionResponse(long submissionId, boolean accepted, String message) {
        this.submissionId = submissionId;
        this.accepted = accepted;
        this.message = message;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }
}
