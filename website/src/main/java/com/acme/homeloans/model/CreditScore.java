package com.acme.homeloans.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Credit score.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditScore {

    private int score;

    public CreditScore() { }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
