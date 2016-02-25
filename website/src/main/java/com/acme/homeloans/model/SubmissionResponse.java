package com.acme.homeloans.model;

/**
 * Submission response.
 */
public class SubmissionResponse {
    private boolean accepted;
    private String message;

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

    @Override
    public String toString() {
        return "SubmissionResponse{" +
                "accepted=" + accepted +
                ", message='" + message + '\'' +
                '}';
    }
}
