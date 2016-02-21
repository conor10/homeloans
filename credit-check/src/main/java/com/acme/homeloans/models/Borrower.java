package com.acme.homeloans.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Borrower data bean.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Borrower {

    private String email;
    private float price;
    private float loanAmount;
    private float salary;

    public Borrower() { }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(float loanAmount) {
        this.loanAmount = loanAmount;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Borrower{" +
                "email='" + email + '\'' +
                ", price=" + price +
                ", loanAmount=" + loanAmount +
                ", salary=" + salary +
                '}';
    }
}
