package com.bank.app.service.AccountService;

import com.bank.app.model.Account;

public interface AccountServiceImpl {
    public boolean addAccount(Account infoAccount);

    public Account getAccount(String accountID);

    public boolean withdraw(String accountId, double amount);

    public boolean deposit(String accountId, double amount);

    public boolean transfer(String fromId, String toId, double amount);

    public void viewTransactionHistory(String id);

    public void deleteAccount(String accountID);

}
