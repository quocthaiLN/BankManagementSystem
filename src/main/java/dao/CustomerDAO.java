package main.java.dao;
import java.sql.*;

import main.java.model.Customer;

public class CustomerDAO extends DAO<Customer> {
    @Override
    public void insert(Customer customer) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = this.getConnection();
            String query = "INSERT INTO customer VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            
        }catch(Exception e) {
            System.out.println("insert customer method error: " + e);
        }finally {
            this.close(conn, pst);
        }
    }

    @Override
    public void delete(Customer customer) {

    }

    @Override
    public void update(Customer customer) {

    }
}
