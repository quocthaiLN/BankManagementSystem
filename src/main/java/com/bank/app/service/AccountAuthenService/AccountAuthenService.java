package com.bank.app.service.AccountAuthenService;

import com.bank.app.model.AccountAuthen;
import com.bank.app.dao.AccountAuthenDAO;

public class AccountAuthenService {

    AccountAuthenDAO accountAuthenDAO = null;

    public boolean addAccountAuthen(AccountAuthen infoAccountAuthen) {
        try {
            // if (infoAccountAuthen == null)
            // throw new "Error: add an null AccountAuthen.";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public AccountAuthen getAccountAuthen(String username) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
