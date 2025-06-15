import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Gọi class UserAccountDAO để lấy UserAccount trong database (nó chỉ thực hieenj một nhiệm vụ đó)
        UserAccountDAO dao = new UserAccountDAO();
        Scanner scan = new Scanner(System.in);

        System.out.print("Username: ");
        String username = scan.next();
        System.out.print("Password: ");
        String password = scan.next();

        // lấy UserAccount
        UserAccount user = dao.getUserAccount(username);
        // kiểm tra nếu user = null, tức là username nhập vào ko tồn tại trong database.
        if(user == null) {
            System.out.println("Your username is wrong");
            return;
        }

        if(user.login(username, password)) {
            System.out.println("Login successfully");
        }else { // login trả về false
            System.out.println("Your username or password is wrong");
        }
        scan.close();
    }
}
