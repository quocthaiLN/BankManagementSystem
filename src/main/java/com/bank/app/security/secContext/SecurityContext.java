package com.bank.app.security.secContext;

import com.bank.app.model.AccountAuthen;

public class SecurityContext {
    private static final ThreadLocal<AccountAuthen> currentUser = new ThreadLocal<>();

    public static void setUser(AccountAuthen accountAuthen) {
        currentUser.set(accountAuthen);
    }

    public static AccountAuthen getUser() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}
