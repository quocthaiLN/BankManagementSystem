package com.bank.app.dao;

import com.bank.app.model.AccountAuthen;
import com.bank.app.model.Transaction;

import java.sql.*;
import java.time.LocalDate;

public class TransactionDAO extends DAO<Transaction>{

    public Transaction getTransactionByID(String transactionID) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = this.getConnection();
            String query = "SELECT * FROM transaction WHERE transaction_id = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, transactionID);

            rs = pst.executeQuery();
            rs.next();

            String id = rs.getString(1);
            String fromAccount = rs.getString(2);
            String toAccount = rs.getString(3);
            String type = rs.getString(4);
            double amount = rs.getDouble(5);
            String currency = rs.getString(6);

            Date sqlDate = rs.getDate(7);
            LocalDate date = sqlDate.toLocalDate();

            String status = rs.getString(8);

            return new Transaction(id, fromAccount, toAccount, type, amount, currency, date, status);

        } catch (SQLException e) {
            System.out.println("getUserAccount methods error: " + e);
        } finally {
            this.close(con, pst, rs);
        }

        return null;
    }

    @Override
    public void insert(Transaction trans) {

    }

    @Override
    public void update(Transaction data) {

    }
}
