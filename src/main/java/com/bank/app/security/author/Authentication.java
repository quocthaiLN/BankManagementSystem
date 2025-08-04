package com.bank.app.security.author;

import com.bank.app.model.AccountAuthen;
import com.bank.app.dao.AccountAuthenDAO;
import com.bank.app.security.hash.*;
import com.bank.app.security.secContext.*;

public class Authentication {

    public Authentication() {
    }

    public AccountAuthen login(String username, String password) {

        AccountAuthenDAO aadao = new AccountAuthenDAO();
        AccountAuthen aa = aadao.getUserAccount(username);

        if (aa == null) {
            throw new SecurityException("Unknown account.");
        }

        String hashedInput = PasswordHasher.hashing(password);
        if (hashedInput != aa.getPassword()) {
            throw new SecurityException("Wrong password.");
        }

        SecurityContext.setUser(aa); //  lưu vào context
        return aa;
    }
}