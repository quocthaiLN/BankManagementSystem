package com.bank.app;

import java.time.LocalDate;
import java.util.Scanner;

import com.bank.app.dao.EmployeeDAO;
import com.bank.app.model.Customer;
//import com.bank.app.model.*;

// import com.bank.app.service.*;
import com.bank.app.model.Employee;
import com.bank.app.service.AccountService.AccountService;
import com.bank.app.service.CustomerService.CustomerService;

//import com.bank.app.dao.*;
//import com.bank.app.security.hash.*;
//import com.bank.app.security.keyStore.*;
//import com.bank.app.security.symmetricEncryption.*;

public class Main {
    public static void main(String[] args) {

        Employee emp = new Employee("NV006", "Mai Anh Tuan", "03667283", "12 Nugyen van cu", "abc@gmail.com", "CN04", "Marketing", "đang hoạt động", LocalDate.now(), "");
        EmployeeDAO empDAO = new EmployeeDAO();
        empDAO.insert(emp);


    }
}
// ! lỗi ở cách đọc file .env
