package com.bank.app.model;

import java.time.LocalDate;

public class Employee implements User {
    private String employeeID;
    private String fullName;
    private String phone;
    private String address;
    private String email;
    private String branchID;
    private String role;
    private String status;
    private LocalDate createdAt;
    private String username;

    public Employee() {
        this.employeeID = "";
        this.fullName = "";
        this.branchID = "";
        this.role = "";
        this.status = "";
        this.createdAt = LocalDate.now(); // lấy ngày hiện tại
    }

    public Employee(String employeeID, String fullName, String phone, String address, String email, String branchID, String role, String status, LocalDate createdAt, String username) {
        this.employeeID = employeeID;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.branchID = branchID;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.username = username;
    }

    public Employee(String employeeID, String name, String branchID, String role, String status, LocalDate date) {
        this.employeeID = employeeID;
        this.fullName = name;
        this.branchID = branchID;
        this.role = role;
        this.status = status;
        this.createdAt = date;
    }

    @Override
    public String getUserType() {
        return "Employee";
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBranchID() {
        return branchID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // display
    @Override
    public String toString() {
        return "Employee{" +
                "employeeID='" + employeeID + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", branchID='" + branchID + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", username='" + username + '\'' +
                '}';
    }
}
