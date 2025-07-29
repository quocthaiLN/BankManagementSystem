package com.bank.app.dao;

import java.sql.*;
import com.bank.app.model.*;
// Đây là lớp dùng để lấy UserAccount từ database -> Ko cần quan tâm logic bên trong.
// Chỉ cần biết: gọi class UserAccountDAO và gọi hàm getUserAccount(string username)
// Hàm này tìm kiếm các tài khoản trong database bằng username.
// Nếu username tồn tại, hàm trả về một UserAccount (như class UserAccount)
// Nếu username không toonf tại, hàm trả về null

public class UserAccountDAO extends DAO<UserAccount> {
    private final String tableName = "user_account";

    public UserAccount getUserAccount(String username) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = this.getConnection();
            String query = "SELECT * FROM " + tableName + " WHERE username = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, username);

            rs = pst.executeQuery();
            rs.next();

            String user = rs.getString(1);
            String pass = rs.getString(2);
            String type = rs.getString(3);
            UserAccount res = new UserAccount(user, pass, type);

            return res;

        } catch (SQLException e) {
            System.out.println("getUserAccount methods error: " + e);
        } finally {
            this.close(con, pst, rs);
        }

        return null;
    }

    @Override
    public void insert(UserAccount user) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = this.getConnection();
            String query = "INSERT INTO " + tableName + " VALUES(?, ?, ?)";
            pst = conn.prepareStatement(query);

            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getUserType());

            int count = pst.executeUpdate();

            System.out.println(count + " rows affected");

        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_KEY_ERROR_CODE) { // Trùng khóa chính
                System.out.println("Duplicate key detected. Please change another username!!");
            } else {
                System.out.println("insert method error: " + e);
            }

        } finally {
            this.close(conn, pst);
        }
    }

    @Override
    public void update(UserAccount newUser) {

    }

    @Override
    public void delete(UserAccount deletedUser) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = this.getConnection();
            String query = "DELETE FROM " + tableName + " WHERE username = ?";
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
