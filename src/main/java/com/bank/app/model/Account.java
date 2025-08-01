package com.bank.app.model;

import java.time.LocalDate;

public class Account {
    private String accountID; // auto increment
    private String customerID;
    private String branchID;
    private String accountType;
    private String currency;
    private double balance;
    private String status;
    private LocalDate openDate;
    private LocalDate closeDate;

    public Account() {
        accountType = "thanh toán";
        currency = "VND";
        balance = 0;
        status = "mở";
        openDate = LocalDate.now(); // lây ngày hiện tại
        closeDate = null;
    }

    public Account(String accountID, String customerID, String branchID, String type, String currency, double balance,
            String status, LocalDate openDate, LocalDate closeDate) {
        this.accountID = accountID;
        this.customerID = customerID;
        this.branchID = branchID;
        this.accountType = type;
        this.currency = currency;
        this.balance = balance;
        this.status = status;
        this.openDate = openDate;
        this.closeDate = closeDate;
    }

    // getter
    public String getAccountID() {
        return this.accountID;
    }

    public String getCustomerID() {
        return this.customerID;
    }

    public String getBranchID() {
        return this.branchID;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public String getCurrency() {
        return this.currency;
    }

    public double getBalance() {
        return this.balance;
    }

    public String getStatus() {
        return this.status;
    }

    public LocalDate getOpenDate() {
        return this.openDate;
    }

    public LocalDate getCloseDate() {
        return this.closeDate;
    }

    // setter
    public void setCustomerID(String cusID) {
        this.customerID = cusID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    // display
    public void display() {
        System.out.printf("Account ID: %d \n", this.accountID);
        System.out.printf("Customer ID: %d \n", this.customerID);
        System.out.printf("Branch ID: %s \n", this.branchID);
        System.out.printf("Account Type: %s \n", this.accountType);
        System.out.printf("Account Currency: %s \n", this.currency);
        System.out.printf("Balance: %f \n", this.balance);
        System.out.printf("Status: %s\n", this.status);
        System.out.printf("Open Date: %s\n", (this.openDate != null) ? this.openDate.toString() : "null");
        System.out.printf("Close Date: %s\n", (this.closeDate != null) ? this.closeDate.toString() : "null");
    }
}
