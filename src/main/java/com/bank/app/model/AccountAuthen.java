package com.bank.app.model;
// Khai báo lớp tài khoản: chỉ có getter và setter, constructor và hàm * login *.

// Đây là lớp trong java (bình thường).

public class AccountAuthen {
    private String username;
    private String password;
    private String userType; // (customer, employee, admin)
    private String account_id;
    private String salt;

    public AccountAuthen(String username, String password, String userType, String account_id) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.account_id = account_id;
    }

    public AccountAuthen(String username, String password, String userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public AccountAuthen(String username, String salt, String password, String userType, String account_id) {
        this.username = username;
        this.salt = salt;
        this.password = password;
        this.userType = userType;
        this.account_id = account_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    // login: trả về true nếu username tồn tại vaf password đúng với username đó.
    /*
     * public boolean login(String username, String password) {
     * UserAccountDAO user_dao = new UserAccountDAO();
     * UserAccount tmp = user_dao.getUserAccount(username);
     * if (tmp == null)
     * return false;
     * 
     * if (!tmp.getPassword().equals(password))
     * return false;
     * 
     * return true;
     * }
     */
}
