package model;

import java.time.LocalDate;

public class Loan {
    private String loanID;
    private int customerID;
    private String loanType;
    private double amount;
    private String currency;
    private double interestRate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String collateralID;

    Loan() {
        this.loanID = "";
        this.customerID = 0;
        this.loanType = "";
        this.amount = 0;
        this.currency = "VND";
        this.interestRate = 0;
        this.startDate = null;
        this.endDate = null;
        this.status = "đang vay";
        this.collateralID = "";
    }

    Loan(String loanID, int customerID, String loanType, double amount, String currency, double interestRate, LocalDate startDate, LocalDate endDate, String status, String collateralID) {
        this.loanID = loanID;
        this.customerID = customerID;
        this.loanType = loanType;
        this.amount = amount;
        this.currency = currency;
        this.interestRate = interestRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.collateralID = collateralID;
    }
}
