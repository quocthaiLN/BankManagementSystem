package main.java.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import main.java.model.Employee;

public class EmployeeDAO extends DAO<Employee> {
    @Override
    public void insert(Employee employee) {

        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = this.getConnection();
            String query = "INSERT INTO customer VALUES(?, ?, ?, ?, ?, ?)";
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
            
        }catch(Exception e) {
            System.out.println("insert employee method error: " + e);
        }finally {
            this.close(conn, pst);
        }
    }

    @Override
    public void delete(Employee employee) {

    }

    @Override
    public void update(Employee employee) {

    }
}
