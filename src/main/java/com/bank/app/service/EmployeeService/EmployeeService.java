package com.bank.app.service.EmployeeService;

import com.bank.app.dao.EmployeeDAO;
import com.bank.app.model.Account;
import com.bank.app.model.Employee;

import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import com.bank.app.security.keyStore.KeyStoreManager;
import com.bank.app.security.symmetricEncryption.AESUtil;

public class EmployeeService implements EmployeeServiceImpl {

    private static final String prefixEntryAlias = "employee_secretkey_id_";
    private EmployeeDAO employeeDAO = null;

    public EmployeeService() {
        employeeDAO = new EmployeeDAO();
    }

    public void addEmployee(Employee infoEmployee) {
        if (infoEmployee == null) {
            throw new NullPointerException("Null information employee");
        }

        try {

            // Đối tượng chứa thông tin đã mã hóa
            Employee encryptedInfoEmployee = new Employee();

            // Tạo secret key dành cho AES
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey secretKey = keyGen.generateKey();

            // Tiến hành mã hóa một số trường nhạy cảm
            encryptedInfoEmployee.setFullName(AESUtil.encrypt(infoEmployee.getFullName(), secretKey));
            encryptedInfoEmployee.setPhone(AESUtil.encrypt(infoEmployee.getPhone(), secretKey));
            encryptedInfoEmployee.setAddress(AESUtil.encrypt(infoEmployee.getAddress(), secretKey));
            encryptedInfoEmployee.setEmail(AESUtil.encrypt(infoEmployee.getEmail(), secretKey));
            encryptedInfoEmployee.setBranchID(AESUtil.encrypt(infoEmployee.getBranchID(), secretKey));
            // ! Chưa biết xử lí role, status, createAT, username ntn?
            encryptedInfoEmployee.setRole(infoEmployee.getRole());
            encryptedInfoEmployee.setStatus(infoEmployee.getStatus());
            encryptedInfoEmployee.setCreatedAt(infoEmployee.getCreatedAt());
            encryptedInfoEmployee.setUsername(infoEmployee.getUsername());

            // Thêm vào csdl và tạo Alias bằng accountID được auto_increment
            String entryAlias = prefixEntryAlias + employeeDAO.insert(encryptedInfoEmployee);

            // Lưu khóa bí mật vào KeyStore
            KeyStoreManager keyStoreManager = new KeyStoreManager();
            keyStoreManager.addEntry(entryAlias, secretKey);
            System.out.println("Employee's infomation has been added.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Employee getEmployee(String employeeID) {
        try {
            // Tạo đối tượng KeyStoreManager
            KeyStoreManager keyStoreManager = new KeyStoreManager();

            // Tạo Alias bằng accountID
            String entryAlias = prefixEntryAlias + employeeID;

            // Lấy secret key
            SecretKey secretKey = keyStoreManager.loadEntry(entryAlias);

            // Lấy thông tin account
            Employee infoEmployee = employeeDAO.getEmployeeByID(employeeID);

            // Tiến hành giải mã một số trường nhạy cảm
            infoEmployee.setFullName(AESUtil.decrypt(infoEmployee.getFullName(), secretKey));
            infoEmployee.setPhone(AESUtil.decrypt(infoEmployee.getPhone(), secretKey));
            infoEmployee.setAddress(AESUtil.decrypt(infoEmployee.getAddress(), secretKey));
            infoEmployee.setEmail(AESUtil.decrypt(infoEmployee.getEmail(), secretKey));
            infoEmployee.setBranchID(AESUtil.decrypt(infoEmployee.getBranchID(), secretKey));
            // ! Chưa biết xử lí role, status, createAT, username ntn?
            System.out.println("Employee's information has been loaded.");
            return infoEmployee;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Employee> getAllEmployee(){
        List<Employee> list = employeeDAO.getAllEmployees();
        return list;
    }

}
