package com.bank.app.dao;

import com.bank.app.model.Employee;

import java.sql.*;
import java.time.LocalDate;

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
            if(!rs.next()) return null;

            String id = rs.getString(1);
            String fullName = rs.getString(2);
            String branchID = rs.getString(3);
            String role = rs.getString(4);
            String status = rs.getString(5);
            LocalDate createdAt = rs.getDate(6).toLocalDate();

            return new Employee(id, fullName, branchID, role, status, createdAt);

        } catch (SQLException e) {
            System.out.println("getEmployee method error: " + e);
        }finally {
            this.close(conn, pst, rs);
        }

        return null;
    }

    @Override
    public void insert(Employee employee) {

        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = this.getConnection();
            String query = "INSERT INTO employee(employee_id, full_name, branch_id, role, status, created_at) VALUES(?, ?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(query);

            pst.setString(1, employee.getID());
            pst.setString(2, employee.getName());
            pst.setString(3, employee.getBranchID());
            pst.setString(4, employee.getRole());
            pst.setString(5, employee.getStatus());

            Date sqlDate = Date.valueOf(employee.getCreatedDate());
            pst.setDate(6, sqlDate);
        
            int count = pst.executeUpdate();

            System.out.println(count + " rows affected");
            
        }catch(SQLException e) {
            if(e.getErrorCode() == DUPLICATE_KEY_ERROR_CODE) { // Trùng khóa chính
                System.out.println("Duplicate key detected. Please change another employee!!");
            }else {
                System.out.println("insert employee method error: " + e);
            }
        }finally {
            this.close(conn, pst);
        }
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

            if(count == 0) return false;
        } catch (Exception e) {
            System.out.println("employee delete method: " + e);
        } finally {
            this.close(conn, pst);
        }

        return true;
    }

    @Override
    public void update(Employee employee) {

    }

    //Hàm kiểm tra xem Employee đã tồn tại trong database chưa (kiểm tra bằng ID)
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
        }catch(Exception e) {
            System.out.println("check employee ID Employee method error: " + e);
        }finally {
            this.close(conn, pst, rs);
        }

        return false;
    }
}
