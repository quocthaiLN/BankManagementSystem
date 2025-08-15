package com.bank.app.security.author;

import com.bank.app.model.AccountAuthen;
import com.bank.app.dao.AccountAuthenDAO;
import com.bank.app.dao.RoleDAO;
import com.bank.app.dao.PermissionDAO;
import com.bank.app.model.UserDetails;
import com.bank.app.security.hash.*;
import com.bank.app.security.secContext.*;

import com.bank.app.model.Permission;
import java.util.Set;
import com.bank.app.model.Role;

public class AuthenUser {

    private final AccountAuthenDAO accountAuthenDAO;
    private final PermissionDAO permissionDAO;
    private final RoleDAO roleDAO;

    public AuthenUser(AccountAuthenDAO accountAuthenDAO, PermissionDAO permissionDAO,
            RoleDAO roleDAO) {
        this.accountAuthenDAO = accountAuthenDAO;
        this.permissionDAO = permissionDAO;
        this.roleDAO = roleDAO;
    }

    public UserDetails login(String username, String password) {
        // 1. Xác thực tài khoản
        AccountAuthen accountAuthen = accountAuthenDAO.getUserAccount(username);
        if (accountAuthen == null) {
            throw new SecurityException("Unknown account.");
        }

        String hashedInput = PasswordHasher.hashing(password);
        if (!hashedInput.equals(accountAuthen.getPassword())) {
            throw new SecurityException("Wrong password.");
        }

        // 2. Lấy tất cả roles (bao gồm kế thừa)
        Role role = roleDAO.getRoleByUserId(accountAuthen.getAccount_id());

        // 3. Lấy tất cả permissions từ roles
        Set<Permission> permissions = permissionDAO.getPermissionsByUserId(accountAuthen.getAccount_id(),
                accountAuthen.getUserType());

        // 4. Tạo UserDetails
        UserDetails userDetails = new UserDetails(
                accountAuthen.getAccount_id(),
                accountAuthen.getUsername(),
                accountAuthen.getUserType(),
                role,
                permissions);

        // 5. Lưu vào SecurityContext
        SecurityContext.setUser(userDetails);

        return userDetails;
    }
}
