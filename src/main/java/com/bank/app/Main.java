package com.bank.app;

import java.time.LocalDate;
import java.util.Scanner;

import com.bank.app.model.Customer;
//import com.bank.app.model.*;

// import com.bank.app.service.*;
import com.bank.app.service.AccountService.AccountService;
import com.bank.app.service.CustomerService.CustomerService;

//import com.bank.app.dao.*;
//import com.bank.app.security.hash.*;
//import com.bank.app.security.keyStore.*;
//import com.bank.app.security.symmetricEncryption.*;

public class Main {
    public static void main(String[] args) {

        Customer customer = new Customer(
                16, // customerID
                "Lê Nguyễn Quốc Thái", // name
                LocalDate.of(2003, 10, 15), // birthDate
                "Nam", // gender
                "123456789012", // identityNumber
                "0909123456", // phone
                "123 Lý Thường Kiệt, Q.10, TPHCM", // address
                "thai.nguyen@example.com", // email
                "Cá nhân", // type
                "Mở", // status
                LocalDate.now() // registerDate
        );
        CustomerService service = new CustomerService();
        service.addCustomer(customer);

        Customer loadedCustomer = service.getCustomer(16);
        loadedCustomer.display();

    }
}
// ! lỗi ở cách đọc file .env
