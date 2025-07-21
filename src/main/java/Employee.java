package main.java;
import java.time.LocalDate;

public class Employee {
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
        this.createdAt = null;
    }
    Employee(String employeeID, String name, String branchID, String role, String status, LocalDate date) {
        this.employeeID = employeeID;
        this.fullName = name;
        this.branchID = branchID;
        this.role = role;
        this.status = status;
        this.createdAt = date;
    }

}
