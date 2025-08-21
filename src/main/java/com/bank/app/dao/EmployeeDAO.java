package com.bank.app.dao;

import com.bank.app.model.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO extends DAO<Employee> {

    public Employee getEmployeeByID(String employeeID) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            String query = "SELECT * FROM employee WHERE employee_id = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, employeeID);

            rs = pst.executeQuery();
            if (!rs.next())
                return null;

            String id = rs.getString(1);
            String fullName = rs.getString(2);
            String branchID = rs.getString(3);
            String role = rs.getString(4);
            String status = rs.getString(5);
            LocalDate createdAt = rs.getDate(6).toLocalDate();
            String phone = rs.getString(7);
            String address = rs.getString(8);
            String email = rs.getString(9);
            String username = rs.getString(10);

            return new Employee(id, fullName, phone, address, email, branchID, role, status, createdAt, username);

        } catch (SQLException e) {
            System.out.println("getEmployee method error: " + e);
        } finally {
            this.close(conn, pst, rs);
        }

        return null;
    }

    @Override
    public String insert(Employee employee) {

        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = this.getConnection();
            String query = "INSERT INTO employee( full_name, branch_id, role, status, created_at, phone, address, email, username) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(query);

            pst.setString(1, employee.getFullName());
            pst.setString(2, employee.getBranchID());
            pst.setString(3, employee.getRole());
            pst.setString(4, employee.getStatus());

            Date sqlDate = Date.valueOf(employee.getCreatedAt());
            pst.setDate(5, sqlDate);

            pst.setString(6, employee.getPhone());
            pst.setString(7, employee.getAddress());
            pst.setString(8, employee.getEmail());
            pst.setString(9, employee.getUsername());
            int count = pst.executeUpdate();

            System.out.println(count + " rows affected");

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getString(1); // account_id vừa sinh
                }
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_KEY_ERROR_CODE) { // Trùng khóa chính
                System.out.println("Duplicate key detected. Please change another employee!!");
            } else {
                System.out.println("insert employee method error: " + e);
            }
        } finally {
            this.close(conn, pst);
        }
        return null;
    }

    public boolean deleteByID(String employeeID) {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = this.getConnection();
            String query = "DELETE FROM employee WHERE employee_id = ?";
            pst = conn.prepareStatement(query);

            pst.setString(1, employeeID);
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
    public void update(Employee employee, String id) {

    }

    // Hàm kiểm tra xem Employee đã tồn tại trong database chưa (kiểm tra bằng ID)
    public boolean isEmpIDExists(String empID) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            String query = "SELECT * FROM customer WHERE identity_number = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, empID);

            rs = pst.executeQuery();

            return rs.next();
        } catch (Exception e) {
            System.out.println("check employee ID Employee method error: " + e);
        } finally {
            this.close(conn, pst, rs);
        }

        return false;
    }
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = this.getConnection();
            String query = "SELECT * FROM Employee";
            pst = con.prepareStatement(query);

            rs = pst.executeQuery();
            while (rs.next()) {
                String empId = rs.getString(1);
                String name = rs.getString(2);
                String phone = rs.getString(3);
                String address = rs.getString(4);
                String email = rs.getString(5);
                String branchId = rs.getString(6);
                String role = rs.getString(7);
                String status = rs.getString(8);

                LocalDate createdAt = null;
                Date sqlCreatedAt = rs.getDate(9);
                if (sqlCreatedAt != null)
                    createdAt = sqlCreatedAt.toLocalDate();

                String username = rs.getString(10);
                employees.add(new Employee(empId, name, phone, address, email, branchId, role, status, createdAt, username));
            }
            return employees;

        } catch (SQLException e) {
            System.out.println("get all customer methods error: " + e);
        } finally {
            this.close(con, pst, rs);
        }
        return new ArrayList<>();
    }
}
