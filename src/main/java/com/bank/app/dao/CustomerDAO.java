package com.bank.app.dao;

import java.sql.*;
import java.time.LocalDate;

import com.bank.app.model.Customer;

public class CustomerDAO extends DAO<Customer> {

    // Lấy customer bằng căng cước công dân
    public Customer getCustomerByIdentityNumber(String identityNumber) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            String query = "SELECT * FROM customer WHERE identity_number = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, identityNumber);

            rs = pst.executeQuery();
            if (!rs.next()) {
                return null;
            }

            int cusID = rs.getInt(1);
            String cusName = rs.getString(2);
            LocalDate cusBirthDate = rs.getDate(3).toLocalDate();
            String cusGender = rs.getString(4);
            String identity = rs.getString(5);
            String phone = rs.getString(6);
            String addr = rs.getString(7);
            String email = rs.getString(8);
            String type = rs.getString(9);
            String status = rs.getString(10);
            LocalDate registerDate = rs.getDate(11).toLocalDate();

            return new Customer(cusID, cusName, cusBirthDate, cusGender, identity, phone, addr, email, type, status,
                    registerDate);

        } catch (SQLException e) {
            System.out.println("getCustomer method error: " + e);
        } finally {
            this.close(conn, pst, rs);
        }

        return null;
    }

    // Lấy customer bằng id
    public Customer getCustomerByID(int customerID) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            String query = "SELECT * FROM customer WHERE customer_id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, customerID);

            rs = pst.executeQuery();
            if (!rs.next()) {
                return null;
            }

            int cusID = rs.getInt(1);
            String cusName = rs.getString(2);
            LocalDate cusBirthDate = rs.getDate(3).toLocalDate();
            String cusGender = rs.getString(4);
            String identity = rs.getString(5);
            String phone = rs.getString(6);
            String addr = rs.getString(7);
            String email = rs.getString(8);
            String type = rs.getString(9);
            String status = rs.getString(10);
            LocalDate registerDate = rs.getDate(11).toLocalDate();

            return new Customer(cusID, cusName, cusBirthDate, cusGender, identity, phone, addr, email, type, status,
                    registerDate);

        } catch (SQLException e) {
            System.out.println("getCustomer method error: " + e);
        } finally {
            this.close(conn, pst, rs);
        }

        return null;
    }

    @Override
    public void insert(Customer customer) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = this.getConnection();
            String query = "INSERT INTO customer(name, birth_date, gender, identity_number, phone, address, email, type, status, register_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(query);

            pst.setString(1, customer.getName());

            Date sqlDate = Date.valueOf(customer.getBirthDate());
            pst.setDate(2, sqlDate);

            pst.setString(3, customer.getGender());
            pst.setString(4, customer.getIndentityNumber());
            pst.setString(5, customer.getPhone());
            pst.setString(6, customer.getAddress());
            pst.setString(7, customer.getEmail());
            pst.setString(8, customer.getType());
            pst.setString(9, customer.getStatus());

            sqlDate = Date.valueOf(customer.getRegisterDate());
            pst.setDate(10, sqlDate);

            int count = pst.executeUpdate();

            System.out.println(count + " rows affected");

        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_KEY_ERROR_CODE) { // Trùng khóa chính
                System.out.println("Duplicate key detected. Please change another customer!!");
            } else {
                System.out.println("insert customer method error: " + e);
            }

        } finally {
            this.close(conn, pst);
        }
    }

    // Delete by Identity Number
    public boolean deleteByIdentityNumber(String identityNumber) {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = this.getConnection();
            String query = "DELETE FROM customer WHERE identity_number = ?";
            pst = conn.prepareStatement(query);

            pst.setString(1, identityNumber);
            int count = pst.executeUpdate();

            if (count == 0)
                return false;
        } catch (Exception e) {
            System.out.println("customer delete method: " + e);
        } finally {
            this.close(conn, pst);
        }

        return true;
    }

    @Override
    public void update(Customer customer) {

    }

    // Hàm kiểm tra customer đã tồn tại trong database chưa (kiểm tra bằng cccd)
    public boolean isIdentityNumberExists(String idNumber) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            String query = "SELECT * FROM customer WHERE identity_number = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, idNumber);

            rs = pst.executeQuery();

            return rs.next();
        } catch (Exception e) {
            System.out.println("check Identity Number customer method error: " + e);
        } finally {
            this.close(conn, pst, rs);
        }

        return false;
    }
}
