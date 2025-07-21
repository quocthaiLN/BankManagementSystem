package main.java;
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


        // Insert
        UserAccount user1 = new UserAccount();
        user1.setUsername("cus06");
        user1.setPassword("pass06");
        user1.setType("customer");

        dao.insert(user1); // insert từ lớp UserAccountDao

        // chèn trùng khóa chính
        UserAccount user2 = new UserAccount();
        user2.setUsername("cus06");
        user2.setPassword("pass07");
        user2.setType("customer");

        dao.insert(user2);

        // Delete
        dao.delete(user1);
        System.out.println("Xoa user1\n");

        dao.delete(user1); // Xóa một user không tồn tại trong bảng
    }
}
