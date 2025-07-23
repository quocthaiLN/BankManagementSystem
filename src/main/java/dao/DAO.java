package main.java.dao;
import java.net.ConnectException;
import java.sql.*;

// Lớp cha cho các lopws DAO dùng để kết nối với database -> ko caanf quan tâm class này.

public abstract class DAO<T> {
    protected final String URL = "jdbc:mysql://localhost:3306/bankingdb";
    protected final String USERNAME = "root";
    protected final String PASSWORD = "23120355Hcmus*"; // password nên thay đổi theo mkhau theo mysql của máy mình.
    static final int DUPLICATE_KEY_ERROR_CODE = 1062; // mã lỗi khi trùng khóa chính


    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    protected void close(Connection conn, PreparedStatement pst) {
        try {
            if (conn != null) conn.close();
            if (pst != null) pst.close();
        }catch(SQLException e) {
            System.out.println(e);
        }
    }
    protected void close(Connection conn, PreparedStatement pst, ResultSet rs) {
        try {
            if(conn != null) conn.close();
            if(pst != null) pst.close();
            if(rs != null) rs.close();
        }catch (SQLException e) {
            System.out.println(e);
        }

    }
    protected void close(Connection conn, Statement st) {
        try {
            if (conn != null) conn.close();
            if (st != null) st.close();
        }catch(SQLException e) {
            System.out.println(e);
        }
    }
    protected void close(Connection conn, Statement st, ResultSet rs) {
        try {
            if(conn != null) conn.close();
            if(st != null) st.close();
            if(rs != null) rs.close();
        }catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Các thao tác với database
    public abstract void insert(T data);
    public abstract void delete(T data);
    public abstract void update(T data);
}
