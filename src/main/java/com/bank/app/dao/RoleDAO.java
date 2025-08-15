package com.bank.app.dao;

import com.bank.app.model.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO extends DAO<Role> {

    /**
     * Lấy Role của một user dựa vào user_id
     */
    public Role getRoleByUserId(String userId) {
        Role role = null;
        String sql = "SELECT r.role_id, r.role_name " +
                "FROM User_Roles ur " +
                "JOIN Roles r ON ur.role_id = r.role_id " +
                "WHERE ur.user_id = ?";

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, userId);
            rs = pst.executeQuery();

            if (rs.next()) {
                int roleId = rs.getInt("role_id");
                String roleName = rs.getString("role_name");
                role = new Role(roleId, roleName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pst, rs);
        }

        return role;
    }

    @Override
    public String insert(Role data) {
        // Có thể triển khai nếu cần thêm Role vào DB
        String sql = "INSERT INTO Roles (role_id, role_name) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, data.getRoleID());
            pst.setString(2, data.getRoleName());
            pst.executeUpdate();
            return "Insert success";
        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_KEY_ERROR_CODE) {
                return "Duplicate role_id";
            }
            e.printStackTrace();
            return "Insert failed";
        } finally {
            close(conn, pst);
        }
    }

    @Override
    public void update(Role data, String id) {
        String sql = "UPDATE Roles SET role_name = ? WHERE role_id = ?";
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, data.getRoleName());
            pst.setInt(2, data.getRoleID());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pst);
        }
    }
}