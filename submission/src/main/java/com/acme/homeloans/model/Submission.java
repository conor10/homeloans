package com.acme.homeloans.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Our submission.
 */
@Entity
public class Submission {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String email;
    private double price;
    private double loanAmount;
    private double salary;

    private int creditScore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    @Override
    public String toString() {
        return "Submission{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", price=" + price +
                ", loanAmount=" + loanAmount +
                ", salary=" + salary +
                ", creditScore=" + creditScore +
                '}';
    }
}
