package com.bank.app.service.EmployeeService;

import com.bank.app.model.Account;
import com.bank.app.model.Customer;

import java.util.List;

public interface EmployeeServiceImpl {
    boolean addAccount(Account account, Customer customer);
    void addCustomer(Customer customer);
    boolean deposit(String accountIdDest, double amount);
    List<String> viewTransactionHistory(String accountID);
}
