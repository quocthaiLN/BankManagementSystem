
import java.time.LocalDate;
import java.util.Scanner;

import dao.AccountDAO;
import dao.CustomerDAO;
import dao.EmployeeDAO;
import dao.UserAccountDAO;
import model.Account;
import model.Customer;
import model.Employee;
import model.UserAccount;

public class Main {
    public static void main(String[] args) {
        // Gọi class UserAccountDAO để lấy UserAccount trong database (nó chỉ thực hieenj một nhiệm vụ đó)
//        UserAccountDAO dao = new UserAccountDAO();
//        Scanner scan = new Scanner(System.in);
//
//        System.out.print("Username: ");
//        String username = scan.next();
//        System.out.print("Password: ");
//        String password = scan.next();
//
//        // lấy UserAccount
//        UserAccount user = dao.getUserAccount(username);
//        // kiểm tra nếu user = null, tức là username nhập vào ko tồn tại trong database.
//        if(user == null) {
//            System.out.println("Your username is wrong");
//            return;
//        }
//
//        if(user.login(username, password)) {
//            System.out.println("Login successfully");
//        }else { // login trả về false
//            System.out.println("Your username or password is wrong");
//        }
//
//        scan.close();
//
//
//        // Insert
//        UserAccount user1 = new UserAccount();
//        user1.setUsername("cus06");
//        user1.setPassword("pass06");
//        user1.setType("customer");
//
//        dao.insert(user1); // insert từ lớp UserAccountDao
//
//        // chèn trùng khóa chính
//        UserAccount user2 = new UserAccount();
//        user2.setUsername("cus06");
//        user2.setPassword("pass07");
//        user2.setType("customer");
//
//        dao.insert(user2);
//
//        // Delete
//        dao.delete(user1);
//        System.out.println("Xoa user1\n");
//
//        dao.delete(user1); // Xóa một user không tồn tại trong bảng

//        ========== Customer ============
//        Customer customer = new Customer("Mai Anh Tuấn", LocalDate.parse("1997-05-12"), "Nam", "2301200323323", "023342342", "HCMUS", "mlozzmat@ghcmus.edu.vn", "cá nhân", "mở", LocalDate.now());
//        CustomerDAO customerDAO = new CustomerDAO();
//        customerDAO.insert(customer);
//        if(customerDAO.deleteByIdentityNumber("2301200323323")) {
//            System.out.println("Delete successfully");
//        }else {
//            System.out.println("Delete unsuccessfully cause(id no exists || error))");
//        }

//          ============ Employee ============
//        Employee emp = new Employee("NV006", "Bùi Duy Đăng", "CN01", "Rác", "đang hoạt động", LocalDate.now());
//        EmployeeDAO empDAO = new EmployeeDAO();
//        empDAO.insert(emp);
//        if(empDAO.deleteByID("NV006")) {
//            System.out.println("Delete successfully");
//        }else {
//            System.out.println("Delete unsuccessfully cause(id no exists || error))");
//        }

//        =========== Account =========
//        AccountDAO accountDAO = new AccountDAO();
//
//        if(accountDAO.deleteByID(6)) {
//            System.out.println("Delete successfully");
//        }else {
//            System.out.println("Delete unsuccessfully cause(id no exists || error))");
//        }

//        CustomerDAO customerDao = new CustomerDAO();
//        Customer customer = customerDao.getCustomerByIdentityNumber("112233445");
//        customer.display();

//        EmployeeDAO empDAO = new EmployeeDAO();
//        Employee emp = empDAO.getEmployeeByID("NV002");
//        emp.display();

        AccountDAO accDAO = new AccountDAO();
        Account account = accDAO.getAccountByID(10);
        if(account == null) System.out.println("AccountID does not exist");
        else account.display();
    }
}
