package com.acme.homeloans.models;

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

    @Override
    public String toString() {
        return "CreditScore{" +
                "score=" + score +
                '}';
    }
}
