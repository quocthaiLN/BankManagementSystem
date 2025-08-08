package com.bank.app.service.AccountService;

import com.bank.app.model.Account;
import com.bank.app.model.Customer;

public interface AccountServiceImpl {
    public boolean addAccount(Account infoAccount);

    public Account getAccount(String accountID);

    public boolean withdraw(double amount, String accountId);

    public boolean deposit(double amount, String accountId);

    public boolean tranfer(String fromId, String toId, double amount);
    
    public void viewTransactionHistory(String id);
}
