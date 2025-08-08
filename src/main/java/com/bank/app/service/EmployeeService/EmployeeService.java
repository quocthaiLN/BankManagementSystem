package com.bank.app.service.EmployeeService;

import com.bank.app.dao.AccountDAO;
import com.bank.app.dao.CustomerDAO;
import com.bank.app.dao.TransactionDAO;
import com.bank.app.model.Account;
import com.bank.app.model.Customer;
import com.bank.app.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService implements EmployeeServiceImpl{

    private CustomerDAO customerDAO;
    private AccountDAO accountDAO;

    public EmployeeService() {
        this.customerDAO = new CustomerDAO();
        this.accountDAO = new AccountDAO();
    }

    // Nhân viên hỗ trợ tạo tài khoản
    @Override
    public boolean addAccount(Account account, Customer customer) {

        if(account == null || customer == null) return false;

        // Khách hàng đã từng tạo tài khoản -> Kiểm tra xem căn cước công dân của họ có trong bảng không
        if(customerDAO.isIdentityNumberExists(customer.getIndentityNumber())) {
            account.setCustomerID(customer.getCustomerID());

            accountDAO.insert(account);
        }else { // Khách hàng chưa từng có tài khoản
            this.addCustomer(customer);

            Customer tmp = customerDAO.getCustomerByIdentityNumber(customer.getIndentityNumber());

            if(tmp != null) {
                account.setCustomerID(tmp.getCustomerID());
                accountDAO.insert(account);
            }else
                return false;

        }

        return true;
    }

    @Override
    public void addCustomer(Customer customer) {
        if(!customerDAO.isIdentityNumberExists(customer.getIndentityNumber()))
            customerDAO.insert(customer);
    }

    @Override // nạp tiền
    public boolean deposit(String accountIdDest, double amount) {
        if(amount <= 0 || !accountDAO.isAccountIDExists(accountIdDest)) return false;

        Account account = accountDAO.getAccountByID(accountIdDest);
        accountDAO.updateBalance(account.getBalance() + amount, accountIdDest);

        return true;
    }

    @Override
    public List<String> viewTransactionHistory(String accountID) {
        if(accountID == null || accountID.isEmpty()) return null;
        TransactionDAO transactionDAO = new TransactionDAO();
        List<Transaction> transactions = transactionDAO.getTransactionByAccId(accountID);

        List<String> history = new ArrayList<>();

        for(int i = 0; i < transactions.size(); i++) {
            history.add(transactions.get(i).toString());
        }

        return history;
    }
}
