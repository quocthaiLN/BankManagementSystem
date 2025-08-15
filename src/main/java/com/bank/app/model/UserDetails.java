package com.bank.app.model;

import java.util.Set;

public class UserDetails {
    private String userId;
    private String username;
    private String userType;
    private Role role;
    private Set<Permission> permissions;

    public UserDetails(String userId, String username, String userType,
            Role role, Set<Permission> permissions) {
        this.userId = userId;
        this.username = username;
        this.userType = userType;
        this.role = role;
        this.permissions = permissions;
    }

    public boolean hasRole(Role role) {
        return role == this.role;
    }

    public boolean hasPermission(String permissionName) {
        for (Permission p : permissions) {
            if (p.getPermissionName().equals(permissionName)) {
                return true;
            }
        }
        return false;
    }

}
