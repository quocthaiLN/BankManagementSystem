package com.bank.app.service.AccountService;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.bank.app.dao.AccountDAO;
import com.bank.app.model.Account;
import com.bank.app.model.Transaction;
import com.bank.app.dao.TransactionDAO;
import com.bank.app.security.keyStore.KeyStoreManager;
import com.bank.app.security.symmetricEncryption.AESUtil;

import java.util.List;
import java.util.ArrayList;

public class AccountService implements AccountServiceImpl {
    private static final String prefixEntryAlias = "account_secretkey_id_";
    private AccountDAO accountDAO = null;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public boolean addAccount(Account infoAccount) {
        if (infoAccount == null)
            return false;

        try {

            // Đối tượng chứa thông tin đã mã hóa
            Account encryptedInfoAccount = new Account();

            // Tạo secret key dành cho AES
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey secretKey = keyGen.generateKey();

            // Tạo Alias bằng accountID
            String entryAlias = prefixEntryAlias + infoAccount.getAccountID();

            // Lưu khóa bí mật vào KeyStore
            KeyStoreManager keyStoreManager = new KeyStoreManager();
            keyStoreManager.addEntry(entryAlias, secretKey);

            // Tiến hành mã hóa một số trường nhạy cảm
            encryptedInfoAccount.setCustomerID(AESUtil.encrypt(String.valueOf(infoAccount.getCustomerID()), secretKey));
            encryptedInfoAccount.setBranchID(AESUtil.encrypt(String.valueOf(infoAccount.getBranchID()), secretKey));
            encryptedInfoAccount.setAccountType(infoAccount.getAccountType());
            encryptedInfoAccount.setCurrency(infoAccount.getCurrency());
            encryptedInfoAccount.setBalance(infoAccount.getBalance());
            encryptedInfoAccount.setStatus(infoAccount.getStatus());
            encryptedInfoAccount.setOpenDate(infoAccount.getOpenDate());
            encryptedInfoAccount.setCloseDate(infoAccount.getCloseDate());

            // Thêm vào csdl
            accountDAO.insert(encryptedInfoAccount);
            System.out.println("Account's infomation has been added.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public Account getAccount(String accountID) {
        try {
            // Tạo đối tượng KeyStoreManager
            KeyStoreManager keyStoreManager = new KeyStoreManager();

            // Tạo Alias bằng accountID
            String entryAlias = prefixEntryAlias + accountID;

            // Lấy secret key
            SecretKey secretKey = keyStoreManager.loadEntry(entryAlias);

            // Lấy thông tin account
            Account infoAccount = accountDAO.getAccountByID(accountID);

            // Decrypt các thông tin
            infoAccount.setCustomerID(AESUtil.decrypt(infoAccount.getCustomerID(), secretKey));
            infoAccount.setBranchID(AESUtil.decrypt(infoAccount.getBranchID(), secretKey));
            System.out.println("Account's information has been loaded.");
            return infoAccount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean withdraw(double amount, String accountId) {
        Account acc = getAccount(accountId);

        // Nếu acc không tồn tại
        if (acc == null) {
            System.out.println("Account does not exist");
            return false;
        }

        // Nếu số dư không đủ
        if (acc.getBalance() < amount) {
            System.out.println("Not enough balance");
            return false;
        }

        // Thực hiện
        try {
            // Lấy số dư mới sau khi rút tiền rồi cập nhật lại
            double newBalance = acc.getBalance() - amount;
            acc.setBalance(newBalance);

            // Update lại trong csdl
            accountDAO.updateBalance(acc.getBalance(), accountId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deposit(double amount, String accountId) {
        Account acc = getAccount(accountId);

        // Nếu acc không tồn tại
        if (acc == null) {
            System.out.println("Account does not exist");
            return false;
        }

        // Thực hiện
        try {
            // Lấy số dư mới sau khi nạp tiền rồi cập nhật lại
            double newBalance = acc.getBalance() + amount;
            acc.setBalance(newBalance);

            // Update lại trong csdl
            accountDAO.updateBalance(acc.getBalance(), accountId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean tranfer(String fromId, String toId, double amount) {
        Account fromAcc = getAccount(fromId);
        Account toAcc = getAccount(toId);
        // Nếu acc không tồn tại
        if (fromAcc == null || toAcc == null) {
            System.out.println("The source account or the destination account does not exist!");
            return false;
        }

        // Nếu số dư không đủ
        if (fromAcc.getBalance() < amount) {
            System.out.println("Not enough balance");
            return false;
        }

        // Thực hiện
        try {

            // ---------------------Tài khoản nguồn--------------------------
            // Cập nhật số tiền của tài khoản nguồn sau khi thực hiện giao dịch
            double newBalance = fromAcc.getBalance() - amount;
            fromAcc.setBalance(newBalance);

            // Update lại tài khoản nguồn trong csdl
            accountDAO.updateBalance(fromAcc.getBalance(), fromId);

            // ---------------------Tài khoản đích--------------------------

            // // Cập nhật số tiền của tài khoản đích sau khi thực hiện giao dịch
            newBalance = toAcc.getBalance() + amount;
            toAcc.setBalance(newBalance);

            // Update lại tài khoản nguồn trong csdl
            accountDAO.updateBalance(toAcc.getBalance(), toId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void viewTransactionHistory(String id) {
        TransactionDAO transactionDAO = new TransactionDAO();
        List<Transaction> transactions = new ArrayList<>();
        transactions = transactionDAO.getTransactionByAccId(id);
        for(Transaction transaction : transactions){
            transaction.display();
        }
    }
}
