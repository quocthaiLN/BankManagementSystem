package com.bank.app.dao;

import com.bank.app.model.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO extends DAO<Role> {

    // Lấy role theo ID
    public Role getByID(int roleID) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            String query = "SELECT * FROM role WHERE role_id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, roleID);
            rs = pst.executeQuery();

            if (rs.next()) {
                String roleName = rs.getString("role_name");
                return new Role(roleID, roleName);
            }

        } catch (SQLException e) {
            System.out.println("getByID error: " + e);
        } finally {
            this.close(conn, pst, rs);
        }

        return null;
    }

    // Lấy danh sách tất cả role
    public List<Role> getAll() {
        List<Role> roles = new ArrayList<>();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            String query = "SELECT * FROM role";
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("role_id");
                String name = rs.getString("role_name");
                roles.add(new Role(id, name));
            }

        } catch (SQLException e) {
            System.out.println("getAll error: " + e);
        } finally {
            this.close(conn, st, rs);
        }

        return roles;
    }

    // Thêm role mới
    @Override
    public void insert(Role role) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = this.getConnection();
            String query = "INSERT INTO role (role_id, role_name) VALUES (?, ?)";
            pst = conn.prepareStatement(query);
            pst.setInt(1, role.getRoleID());
            pst.setString(2, role.getRoleName());

            int count = pst.executeUpdate();
            System.out.println(count + " rows affected");

        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_KEY_ERROR_CODE) {
                System.out.println("Duplicate role ID. Please choose a different one!");
            } else {
                System.out.println("Insert error: " + e);
            }
        } finally {
            this.close(conn, pst);
        }
    }

    // Cập nhật tên role theo ID
    @Override
    public void update(Role role, String id) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = this.getConnection();
            String query = "UPDATE role SET role_name = ? WHERE role_id = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, role.getRoleName());
            pst.setInt(2, role.getRoleID());

            int count = pst.executeUpdate();
            System.out.println(count + " rows affected");

        } catch (SQLException e) {
            System.out.println("Update error: " + e);
        } finally {
            this.close(conn, pst);
        }
    }

    // Xóa role theo ID
    public boolean deleteByID(int roleID) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = this.getConnection();
            String query = "DELETE FROM role WHERE role_id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, roleID);

            int count = pst.executeUpdate();
            return count > 0;

        } catch (SQLException e) {
            System.out.println("Delete error: " + e);
        } finally {
            this.close(conn, pst);
        }

        return false;
    }
}
