package com.bank.app.model;

import java.time.LocalDate;

public class Employee implements User {
    private String employeeID;
    private String fullName;
    private String branchID;
    private String role;
    private String status;
    private LocalDate createdAt;

    Employee() {
        this.employeeID = "";
        this.fullName = "";
        this.branchID = "";
        this.role = "";
        this.status = "";
        this.createdAt = LocalDate.now(); // lấy ngày hiện tại
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

    // getter
    public String getID() {
        return this.employeeID;
    }

    public String getName() {
        return this.fullName;
    }

    public String getBranchID() {
        return this.branchID;
    }

    public String getRole() {
        return this.role;
    }

    public String getStatus() {
        return this.status;
    }

    public LocalDate getCreatedDate() {
        return this.createdAt;
    }

    public void display() {
        System.out.printf("Employee Name: %s \n", this.fullName);
        System.out.printf("Employee Branch ID: %s \n", this.branchID);
        System.out.printf("Employee Role: %s \n", this.role);
        System.out.printf("Employee Status: %s \n", this.status);
        System.out.printf("Employee Created Day: %s \n", this.createdAt.toString());
    }

}
