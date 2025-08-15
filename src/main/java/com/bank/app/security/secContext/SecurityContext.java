package com.bank.app.security.secContext;

import com.bank.app.model.Permission;
import com.bank.app.model.Role;
import com.bank.app.model.UserDetails;

public class SecurityContext {
    private static final ThreadLocal<UserDetails> context = new ThreadLocal<>();

    public static void setUser(UserDetails userDetails) {
        context.set(userDetails);
    }

    public static UserDetails getUser() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }

    public static boolean hasPermission(String permissionName) {
        UserDetails user = getUser();
        if (user == null)
            return false;
        return user.hasPermission(permissionName);
    }

    public static boolean hasRole(Role role) {
        UserDetails user = getUser();
        if (user == null)
            return false;
        return user.hasRole(role);
    }

}
