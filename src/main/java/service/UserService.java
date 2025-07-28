package service;
import dao.CustomerDAO;
import dao.EmployeeDAO;
import model.Customer;
import model.Employee;
import model.User;
import java.sql.Connection;
import java.sql.Statement;

public class UserService {
    private CustomerDAO customerDAO;
    private EmployeeDAO employeeDAO;

    UserService() {
        this.customerDAO = new CustomerDAO();
        this.employeeDAO = new EmployeeDAO();
    };


    // Thêm một người dùng mới
    public boolean addNewUser(User newUser) {
        if(newUser == null) return false;
        String type = newUser.getUserType();
        // Kiểm tra xem user là ai (customer hay employee)
        if(type.equals("Customer")) {
            Customer cus = (Customer)newUser;
            // kiểm tra xem customer đã tồn tại trong database chưa
            if(customerDAO.isIdentityNumberExists(cus.getIndentityNumber())) {
                return false;
            }
            // nếu chưa thì thêm vào db
            customerDAO.insert(cus);
        }else if(type.equals("Employee")) {
            Employee emp = (Employee) newUser;
            if(employeeDAO.isEmpIDExists(emp.getID())) {
                return false;
            }

            employeeDAO.insert(emp);
        }

        return true;
    }

}
