package com.bank.app.service.AccountService;

import com.bank.app.model.Account;
import com.bank.app.model.Customer;

public interface AccountServiceImpl {
    public boolean addAccount(Account infoAccount);

    public Account getAccount(String accountID);
}
