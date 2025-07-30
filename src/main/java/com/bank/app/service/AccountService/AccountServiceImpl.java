package com.bank.app.service.AccountService;

import com.bank.app.model.Account;
import com.bank.app.model.Customer;

public interface AccountServiceImpl {
    boolean addAccount(Customer infoCustomer, Account insertedAccount);
}
