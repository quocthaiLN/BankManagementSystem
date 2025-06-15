import java.sql.*;

// Đây là lớp dùng để lấy UserAccount từ database -> Ko cần quan tâm logic bên trong.
// Chỉ cần biết: gọi class UserAccountDAO và gọi hàm getUserAccount(string username)
// Hàm này tìm kiếm các tài khoản trong database bằng username.
// Nếu username tồn tại, hàm trả về một UserAccount (như class UserAccount)
// Nếu username không toonf tại, hàm trả về null

public class UserAccountDAO extends DAO {

    public UserAccount getUserAccount(String username) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = this.getConnection();
            String query = "select * from user_account where username = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, username);

            rs = pst.executeQuery();
            rs.next();

            String user = rs.getString(1);
            String pass = rs.getString(2);
            String type = rs.getString(3);
            int reference_id = rs.getInt(4);
            UserAccount res = new UserAccount(user, pass, type, reference_id);

            return res;

        } catch (SQLException e) {
            System.out.println(e);
        } finally {

            this.close(con, pst, rs);

        }

        return null;
    }
}
