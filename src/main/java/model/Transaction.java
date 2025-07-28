package model;

import java.time.LocalDate;

public class Transaction {
    private String transactionID;
    private int fromAccount; // Account ID
    private int toAccount; // AccountID
    private String transactionType;
    private double amount;
    private String currency;
    private LocalDate date;
    private String status; // thành công || thất baại || đang chờ xử lý

    Transaction() {
        this.transactionID = "";
        this.fromAccount = 0;
        this.toAccount = 0;
        this.transactionType = "";
        this.amount = 0;
        this.currency = "VND";
        this.date = null;
        this.status = "thành công";
    }

    Transaction(String transactionID, int fromAccount, int toAccount, String transactionType, double amount, String currency, LocalDate date, String status) {
        this.transactionID = transactionID;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.transactionType = transactionType;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.status = status;
    }
}
