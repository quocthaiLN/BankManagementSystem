package com.bank.app.dao;

import com.bank.app.model.Account;

import java.sql.*;
import java.time.LocalDate;

public class AccountDAO extends DAO<Account> {

    public Account getAccountByCustomerID(String customerID) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            String query = "SELECT * FROM account WHERE customer_id = ?";
            pst = conn.prepareStatement(query);

            pst.setString(1, customerID);
            rs = pst.executeQuery();

            if (!rs.next())
                return null;

            String id = rs.getString(1);
            String cusID = rs.getString(2);
            String branchID = rs.getString(3);
            String type = rs.getString(4);
            String currency = rs.getString(5);
            double balance = rs.getDouble(6);
            String status = rs.getString(7);

            LocalDate openDate = null;
            Date sqlOpenDate = rs.getDate(8);
            if (sqlOpenDate != null)
                openDate = sqlOpenDate.toLocalDate();

            LocalDate closeDate = null;
            Date sqlCloseDate = rs.getDate(9);
            if (sqlCloseDate != null)
                closeDate = sqlCloseDate.toLocalDate();

            return new Account(id, cusID, branchID, type, currency, balance, status, openDate, closeDate);

        } catch (SQLException e) {
            System.out.println("getAccountByCustomerID method error: " + e);
        } finally {
            this.close(conn, pst, rs);
        }

        return null;
    }

    public Account getAccountByID(String accountID) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            String query = "SELECT * FROM account WHERE account_id = ?";
            pst = conn.prepareStatement(query);

            pst.setString(1, accountID);
            rs = pst.executeQuery();

            if (!rs.next())
                return null;

            String id = rs.getString(1);
            String cusID = rs.getString(2);
            String branchID = rs.getString(3);
            String type = rs.getString(4);
            String currency = rs.getString(5);
            double balance = rs.getDouble(6);
            String status = rs.getString(7);

            LocalDate openDate = null;
            Date sqlOpenDate = rs.getDate(8);
            if (sqlOpenDate != null)
                openDate = sqlOpenDate.toLocalDate();

            LocalDate closeDate = null;
            Date sqlCloseDate = rs.getDate(9);
            if (sqlCloseDate != null)
                closeDate = sqlCloseDate.toLocalDate();

            return new Account(id, cusID, branchID, type, currency, balance, status, openDate, closeDate);

        } catch (SQLException e) {
            System.out.println("getAccount method error: " + e);
        } finally {
            this.close(conn, pst, rs);
        }

        return null;
    }

    @Override
    public String insert(Account acc) {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = this.getConnection();
            String query = "INSERT INTO account (branch_id, account_type, currency, balance, status, open_date, close_date) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, acc.getBranchID());
            pst.setString(2, acc.getAccountType());
            pst.setString(3, acc.getCurrency());
            pst.setDouble(4, acc.getBalance());
            pst.setString(5, acc.getStatus());
            pst.setDate(6, Date.valueOf(acc.getOpenDate()));

            if (acc.getCloseDate() == null) {
                pst.setNull(7, Types.DATE);
            } else {
                pst.setDate(7, Date.valueOf(acc.getCloseDate()));
            }

            int count = pst.executeUpdate();
            System.out.println(count + " rows affected");

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getString(1); // account_id vừa sinh
                }
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_KEY_ERROR_CODE) {
                System.out.println("Duplicate key detected. Please change another account!!");
            } else {
                System.out.println("insert account method error: " + e);
            }
        } finally {
            this.close(conn, pst);
        }

        return null;
    }

    // Xóa theo ID
    public boolean deleteByID(String accountID) {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = this.getConnection();
            String query = "DELETE FROM account WHERE account_id = ?";
            pst = conn.prepareStatement(query);

            pst.setString(1, accountID);
            int count = pst.executeUpdate();

            if (count == 0)
                return false;
        } catch (Exception e) {
            System.out.println("employee delete method: " + e);
        } finally {
            this.close(conn, pst);
        }

        return true;
    }

    @Override
    public void update(Account acc, String accountId) {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = this.getConnection();
            String query = "UPDATE ACCOUNT SET customer_id = ?, branch_id = ?, account_type = ?, currency = ?, balance = ?, status = ?, open_date  = ?, close_date = ? WHERE account_id = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, acc.getCustomerID());
            pst.setString(2, acc.getBranchID());
            pst.setString(3, acc.getAccountType());
            pst.setString(4, acc.getCurrency());
            pst.setDouble(5, acc.getBalance());
            pst.setString(6, acc.getStatus());

            Date sqlDate = Date.valueOf(acc.getOpenDate());
            pst.setDate(7, sqlDate);

            // xử lý date null trước khi truyền vào sql
            if (acc.getCloseDate() == null) {
                pst.setNull(8, Types.DATE);
            } else {
                sqlDate = Date.valueOf(acc.getCloseDate());
                pst.setDate(8, sqlDate);
            }
            pst.setString(9, accountId);
            int count = pst.executeUpdate();

            if (count == 0) {
                System.out.println("No account was updated. Possibly invalid account_id: " + accountId);
            } else {
                System.out.println("Account updated successfully.");
            }
            return;
        } catch (Exception e) {
            System.out.println("account update method err: " + e);
        } finally {
            this.close(conn, pst);
        }

        return;
    }

    public void updateBalance(double balance, String accountId) {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = this.getConnection();
            String query = "UPDATE ACCOUNT SET balance = ? WHERE account_id = ?";
            pst = conn.prepareStatement(query);
            pst.setDouble(1, balance);
            pst.setString(2, accountId);
            int count = pst.executeUpdate();

            if (count == 0) {
                System.out.println("No account was updated. Possibly invalid account_id: " + accountId);
            } else {
                System.out.println("Balance updated successfully.");
            }
            return;
        } catch (Exception e) {
            System.out.println("Balance update method err: " + e);
        } finally {
            this.close(conn, pst);
        }
        return;
    }
}
