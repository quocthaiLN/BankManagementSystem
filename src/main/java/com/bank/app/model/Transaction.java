package com.bank.app.model;

import java.time.LocalDate;

public class Transaction {
    private String transactionID;
    private String fromAccount; // Account ID
    private String toAccount; // AccountID
    private String transactionType;
    private double amount;
    private String currency;
    private LocalDate date;
    private String status; // thành công || thất baại || đang chờ xử lý

    Transaction() {
        this.transactionID = "";
        this.fromAccount = "";
        this.toAccount = "";
        this.transactionType = "";
        this.amount = 0;
        this.currency = "VND";
        this.date = null;
        this.status = "thành công";
    }

    public Transaction(String transactionID, String fromAccount, String toAccount, String transactionType,
            double amount, String currency, LocalDate date, String status) {
        this.transactionID = transactionID;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.transactionType = transactionType;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.status = status;
    }

    public Transaction(String fromAccount, String toAccount, String transactionType, double amount, String currency,
            LocalDate date, String status) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.transactionType = transactionType;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.status = status;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void display() {
        System.out.print(this.transactionID + ", ");
        System.out.print(this.fromAccount + ", ");
        System.out.print(this.toAccount + ", ");
        System.out.print(this.transactionType + ", ");
        System.out.print(this.amount + ", ");
        System.out.print(this.currency + ", ");
        if (this.date != null) {
            System.out.print(this.date.toString() + ", ");
        } else {
            System.out.print("NULL, ");
        }
        System.out.println(this.status);
    }

}
