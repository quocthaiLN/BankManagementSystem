package com.bank.app.model;

import com.bank.app.dao.*;
// Khai báo lớp tài khoản: chỉ có getter và setter, constructor và hàm * login *.

// Đây là lớp trong java (bình thường).

public class UserAccount {
    private String username;
    private String password;
    private String userType; // (customer, employee)

    UserAccount() {
        this.username = "";
        this.password = "";
        this.userType = "customer";
    }

    public UserAccount(String username, String password, String userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    // setter
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.userType = type;
    }

    // getter
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUserType() {
        return this.userType;
    }

    // login: trả về true nếu username tồn tại vaf password đúng với username đó.
    public boolean login(String username, String password) {
        UserAccountDAO user_dao = new UserAccountDAO();
        UserAccount tmp = user_dao.getUserAccount(username);
        if (tmp == null)
            return false;

        if (!tmp.getPassword().equals(password))
            return false;

        return true;
    }
}
