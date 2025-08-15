package com.bank.app.service.AccountAuthenService;

import com.bank.app.model.AccountAuthen;

public interface AccountAuthemServiceImpl {

    public boolean addAccountAuthen(AccountAuthen infoAccountAuthen);

    public AccountAuthen getAccountAuthen(String username);
}
