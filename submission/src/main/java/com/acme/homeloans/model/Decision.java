package com.acme.homeloans.model;

import javax.persistence.*;

/**
 * Application descision.
 */
@Entity
public class Decision {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private boolean accepted;

    private String message;

    @ManyToOne
    private Submission submission;

    public Decision() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }
}
