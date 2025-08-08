package com.bank.app.dao;

import com.bank.app.model.Permission;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PermissionDAO extends DAO<Permission> {

    // Truy vấn permission theo ID
    public Permission getByID(int permissionID) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            String query = "SELECT * FROM permission WHERE permission_id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, permissionID);
            rs = pst.executeQuery();

            if (rs.next()) {
                String name = rs.getString("permission_name");
                return new Permission(permissionID, name);
            }

        } catch (SQLException e) {
            System.out.println("getByID error: " + e);
        } finally {
            this.close(conn, pst, rs);
        }

        return null;
    }

    // Lấy toàn bộ permission
    public List<Permission> getAll() {
        List<Permission> permissions = new ArrayList<>();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            String query = "SELECT * FROM permission";
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("permission_id");
                String name = rs.getString("permission_name");
                permissions.add(new Permission(id, name));
            }

        } catch (SQLException e) {
            System.out.println("getAll error: " + e);
        } finally {
            this.close(conn, st, rs);
        }

        return permissions;
    }

    // Thêm mới permission
    @Override
    public void insert(Permission p) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = this.getConnection();
            String query = "INSERT INTO permission (permission_id, permission_name) VALUES (?, ?)";
            pst = conn.prepareStatement(query);
            pst.setInt(1, p.getPermissionID());
            pst.setString(2, p.getPermissionName());

            int count = pst.executeUpdate();
            System.out.println(count + " rows affected");

        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_KEY_ERROR_CODE) {
                System.out.println("Duplicate permission ID. Please choose a different one!");
            } else {
                System.out.println("Insert error: " + e);
            }
        } finally {
            this.close(conn, pst);
        }
    }

    // Cập nhật permission
    @Override
    public void update(Permission p, String id) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = this.getConnection();
            String query = "UPDATE permission SET permission_name = ? WHERE permission_id = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, p.getPermissionName());
            pst.setInt(2, p.getPermissionID());

            int count = pst.executeUpdate();
            System.out.println(count + " rows affected");

        } catch (SQLException e) {
            System.out.println("Update error: " + e);
        } finally {
            this.close(conn, pst);
        }
    }

    // Xóa permission theo ID
    public boolean deleteByID(int permissionID) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = this.getConnection();
            String query = "DELETE FROM permission WHERE permission_id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, permissionID);

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
