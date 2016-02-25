package com.acme.homeloans.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Borrower data bean.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Borrower {

    private String email;
    private double price;
    private double loanAmount;
    private double salary;

    public Borrower() { }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
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
