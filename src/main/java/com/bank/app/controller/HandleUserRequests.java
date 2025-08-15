package com.bank.app.controller;

import com.bank.app.model.Customer;
import com.bank.app.model.Employee;
import com.bank.app.model.Account;
import com.bank.app.security.secContext.SecurityContext;
import com.bank.app.service.AccountService.AccountService;
import com.bank.app.service.CustomerService.CustomerService;
import com.bank.app.model.PermissionNames;
import com.bank.app.service.TransactionService.TransactionService;
import com.bank.app.service.EmployeeService.EmployeeService;

public class HandleUserRequests {
    private PermissionNames permissionNames = new PermissionNames();
    private CustomerService customerService = new CustomerService();
    private AccountService accountService = new AccountService();
    private TransactionService transactionService = new TransactionService();
    private EmployeeService employeeService = new EmployeeService();

    // Customer Actions
    public void viewCustomerInfo(String customerId) {
        if (!SecurityContext.hasPermission(permissionNames.viewCustomer)) {
            throw new SecurityException("User don't have permission: " + permissionNames.viewCustomer);
        }
        Customer customer = customerService.getCustomer(customerId);
        System.out.println(customer);
    }

    public void addNewCustomer(Customer customerInfo) {
        if (!SecurityContext.hasPermission(permissionNames.addCustomer)) {
            throw new SecurityException("User don't have permission: " + permissionNames.addCustomer);
        }
        customerService.addCustomer(customerInfo);
        System.out.println("Add new customer successfully");
    }

    public void viewAccountInfo(String accountID, String accountType) {
        if (!SecurityContext.hasPermission(permissionNames.viewAccount)) {
            throw new SecurityException("User don't have permission: " + permissionNames.viewAccount);
        }
        Account account = accountService.getAccount(accountID);
        System.out.println(account);
    }

    public void deleteAccount(String accountID, String accountType) {
        if (!SecurityContext.hasPermission(permissionNames.deleteAccount)) {
            throw new SecurityException("User don't have permission: " + permissionNames.deleteAccount);
        }
        accountService.deleteAccount(accountID);
        System.out.println("Account deleted successfully");
    }

    public void depositFunds(String accountID, double amount) {
        if (!SecurityContext.hasPermission(permissionNames.depositFunds)) {
            throw new SecurityException("User don't have permission: " + permissionNames.depositFunds);
        }
        accountService.deposit(accountID, amount);
        transactionService.addTransaction(null, accountID, "deposit", amount, null, null);
        System.out.println("Deposited " + amount + " to account " + accountID);
    }

    public void withdrawFunds(String accountID, double amount) {
        if (!SecurityContext.hasPermission(permissionNames.withdrawFunds)) {
            throw new SecurityException("User don't have permission: " + permissionNames.withdrawFunds);
        }
        accountService.withdraw(accountID, amount);
        transactionService.addTransaction(accountID, null, "withdraw", amount, null, null);
        System.out.println("Withdrew " + amount + " from account " + accountID);
    }

    public void transferFunds(String fromAccountID, String toAccountID, double amount) {
        if (!SecurityContext.hasPermission(permissionNames.transferFunds)) {
            throw new SecurityException("User don't have permission: " + permissionNames.transferFunds);
        }
        accountService.transfer(fromAccountID, toAccountID, amount);
        accountService.transfer(fromAccountID, toAccountID, amount);
        System.out.println("Transferred " + amount + " from " + fromAccountID + " to " + toAccountID);
    }

    // Empoyee action
    public void addEmployee(Employee employeeInfo) {
        if (!SecurityContext.hasPermission(permissionNames.addEmployee)) {
            throw new SecurityException("User don't have permission: " + permissionNames.addEmployee);
        }
        employeeService.addEmployee(employeeInfo);
        System.out.println("Added new employee successfully");
    }

    public void viewEmployee(String employeeId) {
        if (!SecurityContext.hasPermission(permissionNames.viewEmployee)) {
            throw new SecurityException("User don't have permission: " + permissionNames.viewEmployee);
        }
        Employee employee = employeeService.getEmployee(employeeId);
        System.out.println(employee);
    }

    public void viewAllCustomer() {
        if (!SecurityContext.hasPermission(permissionNames.viewAllCustomer)) {
            throw new SecurityException("User don't have permission: " + permissionNames.viewAllCustomer);
        }
        // Code giúp tui hàm lấy tất cả các customer ở trong CustomerService
    }

    public void viewAllUserAccount() {
        if (!SecurityContext.hasPermission(permissionNames.viewAllUserAccount)) {
            throw new SecurityException("User don't have permission: " + permissionNames.viewAllUserAccount);
        }
        // Code giúp tui hàm lấy tất cả các UserAccount ở trong AccountService
    }

    public void viewAllEmployee() {
        if (!SecurityContext.hasPermission(permissionNames.viewAllEmployee)) {
            throw new SecurityException("User don't have permission: " + permissionNames.viewAllEmployee);
        }
        // Code giúp tui hàm lấy tất cả các Employee ở trong EmployeeService
    }

}