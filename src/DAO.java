import java.net.ConnectException;
import java.sql.*;

// Lớp cha cho các lopws DAO dùng để kết nối với database -> ko caanf quan tâm class này.

public class DAO {
    protected final String URL = "jdbc:mysql://localhost:3306/bankingdb";
    protected final String USERNAME = "root";
    protected final String PASSWORD = "23120355Hcmus*"; // password nên thay đổi theo mkhau theo mysql của máy mình.

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

    protected void close(Connection con, PreparedStatement pst, ResultSet rs) {
        try {
            if(con != null) con.close();
            if(pst != null) pst.close();
            if(rs != null) rs.close();
        }catch (SQLException e) {
            System.out.println(e);
        }

    }

    protected void close(Connection con, Statement st, ResultSet rs) {
        try {
            if(con != null) con.close();
            if(st != null) st.close();
            if(rs != null) rs.close();
        }catch (SQLException e) {
            System.out.println(e);
        }
    }
}
