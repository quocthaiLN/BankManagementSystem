package com.bank.app.service.CustomerService;

import com.bank.app.model.Account;
import com.bank.app.model.Customer;

import java.util.List;

public interface CustomerServiceImpl {
    public boolean addCustomer(Customer infoCustomer);
    public Customer getCustomer(String customerID);
    public List<Account> showAccount(String customerID);
}
