package com.bank.app.service.AccountService;

import com.bank.app.dao.AccountDAO;
import com.bank.app.dao.CustomerDAO;
import com.bank.app.model.Account;
import com.bank.app.model.Customer;

public class AccountService implements AccountServiceImpl {
    private CustomerDAO customerDAO = null;
    private AccountDAO accountDAO = null;

    AccountService() {
        this.customerDAO = new CustomerDAO();
        this.accountDAO = new AccountDAO();
    }

    @Override
    public boolean addAccount(Customer infoCustomer, Account insertedAccount) {
        if (infoCustomer == null || insertedAccount == null)
            return false;

        Account tmp = accountDAO.getAccountByCustomerID(infoCustomer.getCustomerID());

        // người dùng chưa bất kỳ tài khoản nào
        if (tmp == null) {
            // Theem người dùng vào danh sách
            customerDAO.insert(infoCustomer);

            // Thêm tài khoản vào bảng tài khoản
            // 1. set customerID tương ứng với taài khoản này
            insertedAccount.setCustomerID(infoCustomer.getCustomerID());
            accountDAO.insert(insertedAccount);

        } else { // Nếu người này đã có tài khoản
            // thì chỉ cần thêm vào là được
            insertedAccount.setCustomerID(infoCustomer.getCustomerID());
            accountDAO.insert(insertedAccount);

        }

        return true;
    }

}
