package com.bank.app.dao;

import com.bank.app.model.AccountAuthen;

import java.sql.*;

// Đây là lớp dùng để lấy UserAccount từ database -> Ko cần quan tâm logic bên trong.
// Chỉ cần biết: gọi class UserAccountDAO và gọi hàm getUserAccount(string username)
// Hàm này tìm kiếm các tài khoản trong database bằng username.
// Nếu username tồn tại, hàm trả về một UserAccount (như class UserAccount)
// Nếu username không toonf tại, hàm trả về null

public class AccountAuthenDAO extends DAO<AccountAuthen> {

    public AccountAuthen getUserAccount(String username) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = this.getConnection();
            String query = "SELECT * FROM account_authen WHERE username = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, username);

            rs = pst.executeQuery();
            rs.next();

            String user = rs.getString(1);
            String pass = rs.getString(2);
            String type = rs.getString(3);

            String account_id = rs.getString(4);
            String salt = rs.getString(5);
            AccountAuthen res = new AccountAuthen(user, salt, pass, type, account_id);

            return res;

        } catch (SQLException e) {
            System.out.println("getUserAccount methods error: " + e);
        } finally {
            this.close(con, pst, rs);
        }

        return null;
    }

    @Override
    public String insert(AccountAuthen user) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = this.getConnection();
            String query = "INSERT INTO account_authen VALUES(?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(query);

            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getUserType());
            pst.setString(4, user.getAccount_id());
            pst.setString(5, user.getSalt());

            int count = pst.executeUpdate();

            System.out.println(count + " rows affected");
            return null;
        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_KEY_ERROR_CODE) { // Trùng khóa chính
                System.out.println("Duplicate key detected. Please change another username!!");
            } else {
                System.out.println("insert method user_account error: " + e);
            }

        } finally {
            this.close(conn, pst);
        }
        return null;
    }

    @Override
    public void update(AccountAuthen newUser, String id) {

    }

    public void delete(AccountAuthen deletedUser) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = this.getConnection();
            String query = "DELETE FROM account_authen WHERE username = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, deletedUser.getUsername());
            int count = pst.executeUpdate();

            if (count == 0) {
                System.out.println("username does not exist!!");
            } else {
                System.out.println(count + " rows affected");
            }

        } catch (Exception e) {
            System.out.println("delete method error: " + e);
        } finally {
            this.close(conn, pst);
        }
    }
}
