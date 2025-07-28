package dao;

import model.Account;

import java.sql.*;
import java.time.LocalDate;

public class AccountDAO extends DAO<Account> {

    public Account getAccountByCustomerID(int customerID) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            String query = "SELECT * FROM account WHERE customer_id = ?";
            pst = conn.prepareStatement(query);

            pst.setInt(1, customerID);
            rs = pst.executeQuery();

            if(!rs.next()) return null;

            int id = rs.getInt(1);
            int cusID = rs.getInt(2);
            String branchID = rs.getString(3);
            String type = rs.getString(4);
            String currency = rs.getString(5);
            double balance = rs.getDouble(6);
            String status = rs.getString(7);

            LocalDate openDate = null;
            Date sqlOpenDate = rs.getDate(8);
            if(sqlOpenDate != null) openDate = sqlOpenDate.toLocalDate();

            LocalDate closeDate = null;
            Date sqlCloseDate = rs.getDate(9);
            if(sqlCloseDate != null) closeDate = sqlCloseDate.toLocalDate();

            return new Account(id, cusID, branchID, type, currency, balance, status, openDate, closeDate);

        } catch (SQLException e) {
            System.out.println("getAccountByCustomerID method error: " + e);
        }finally {
            this.close(conn, pst, rs);
        }

        return null;
    }

    public Account getAccountByID(int accountID) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            String query = "SELECT * FROM account WHERE account_id = ?";
            pst = conn.prepareStatement(query);

            pst.setInt(1, accountID);
            rs = pst.executeQuery();

            if(!rs.next()) return null;

            int id = rs.getInt(1);
            int cusID = rs.getInt(2);
            String branchID = rs.getString(3);
            String type = rs.getString(4);
            String currency = rs.getString(5);
            double balance = rs.getDouble(6);
            String status = rs.getString(7);

            LocalDate openDate = null;
            Date sqlOpenDate = rs.getDate(8);
            if(sqlOpenDate != null) openDate = sqlOpenDate.toLocalDate();

            LocalDate closeDate = null;
            Date sqlCloseDate = rs.getDate(9);
            if(sqlCloseDate != null) closeDate = sqlCloseDate.toLocalDate();

            return new Account(id, cusID, branchID, type, currency, balance, status, openDate, closeDate);

        } catch (SQLException e) {
            System.out.println("getAccount method error: " + e);
        }finally {
            this.close(conn, pst, rs);
        }

        return null;
    }

    @Override
    public void insert(Account acc) {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = this.getConnection();
            String query = "INSERT INTO account (customer_id, branch_id, account_type, currency, balance, status, open_date, close_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(query);

            pst.setInt(1, acc.getCustomerID());
            pst.setString(2, acc.getBranchID());
            pst.setString(3, acc.getAccountType());
            pst.setString(4, acc.getCurrency());
            pst.setDouble(5, acc.getBalance());
            pst.setString(6, acc.getStatus());

            Date sqlDate = Date.valueOf(acc.getOpenDate());
            pst.setDate(7, sqlDate);

            // xử lý date null trước khi truyền vào sql
            if(acc.getCloseDate() == null) {
                pst.setNull(8, Types.DATE);
            }else {
                sqlDate = Date.valueOf(acc.getCloseDate());
                pst.setDate(8, sqlDate);
            }

            int count = pst.executeUpdate();

            System.out.println(count + " rows affected");

        }catch(SQLException e) {
            if(e.getErrorCode() == DUPLICATE_KEY_ERROR_CODE) { // Trùng khóa chính
                System.out.println("Duplicate key detected. Please change another account!!");
            }else {
                System.out.println("insert account method error: " + e);
            }
        }finally {
            this.close(conn, pst);
        }
    }

    // Xóa theo ID
    public boolean deleteByID(int accountID) {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = this.getConnection();
            String query = "DELETE FROM account WHERE account_id = ?";
            pst = conn.prepareStatement(query);

            pst.setInt(1, accountID);
            int count = pst.executeUpdate();

            if(count == 0) return false;
        } catch (Exception e) {
            System.out.println("employee delete method: " + e);
        } finally {
            this.close(conn, pst);
        }

        return true;
    }

    @Override
    public void update(Account acc) {

    }
}
