import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        UserAccount user = new UserAccount();
        user.setUsername("cus05");
        user.setPassword("pass05");
        user.setType("customer");

        UserAccountDAO dao = new UserAccountDAO();
        dao.insert(user);
    }
}
