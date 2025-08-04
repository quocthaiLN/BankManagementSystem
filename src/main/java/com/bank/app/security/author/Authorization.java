package com.bank.app.security.author;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Authorization {
    private final Connection connection;

    public Authorization(Connection connection) {
        this.connection = connection;
    }

    public boolean isAuthorized(String userId, String permissionName) {
        Set<String> permissions = getAllPermissionsOfUser(userId);
        return permissions.contains(permissionName);
    }

    private Set<String> getAllPermissionsOfUser(String userId) {
        Set<String> permissionSet = new HashSet<>();

        String sql = "{CALL get_all_permissions_of_user(?)}";

        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String permission = rs.getString("permission_name");
                    permissionSet.add(permission);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return permissionSet;
    }

}
