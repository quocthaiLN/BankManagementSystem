package com.bank.app.service.TransactionService;

import com.bank.app.dao.TransactionDAO;
import com.bank.app.model.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TransactionService {
    private TransactionDAO transactionDAO = new TransactionDAO();

    /**
     * Tạo transaction mới và lưu vào DB
     */
    public String addTransaction(String fromAccount, String toAccount, String type,
            double amount, String currency, String status) {
        // Tạo transactionID ngẫu nhiên
        String transactionID = UUID.randomUUID().toString();

        Transaction trans = new Transaction(
                transactionID,
                fromAccount,
                toAccount,
                type,
                amount,
                currency,
                LocalDate.now(),
                status);

        String insertedID = transactionDAO.insert(trans);
        if (insertedID != null) {
            System.out.println("Transaction added: " + insertedID);
        } else {
            System.out.println("Failed to add transaction");
        }

        return insertedID;
    }

    /**
     * Lấy transaction theo ID
     */
    public Transaction getTransactionByID(String transactionID) {
        return transactionDAO.getTransactionByID(transactionID);
    }

    /**
     * Lấy danh sách transaction của một account
     */
    public List<Transaction> getTransactionByAccountID(String accountID) {
        return transactionDAO.getTransactionByAccId(accountID);
    }
}
