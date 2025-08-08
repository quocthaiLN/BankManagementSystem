package com.bank.app;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.bank.app.dao.EmployeeDAO;
import com.bank.app.model.Account;
import com.bank.app.model.Customer;
//import com.bank.app.model.*;

// import com.bank.app.service.*;
import com.bank.app.model.Employee;
import com.bank.app.service.AccountService.AccountService;
import com.bank.app.service.CustomerService.CustomerService;
import com.bank.app.service.EmployeeService.EmployeeService;

//import com.bank.app.dao.*;
//import com.bank.app.security.hash.*;
//import com.bank.app.security.keyStore.*;
//import com.bank.app.security.symmetricEncryption.*;

public class Main {
    public static void main(String[] args) {

        EmployeeService service = new EmployeeService();
        List<String> history = service.viewTransactionHistory("0000000001");

        for(int i = 0; i < history.size(); i++) {
            System.out.println(history.get(i));
        }
    }
}
