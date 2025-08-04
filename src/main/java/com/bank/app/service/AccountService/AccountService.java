package com.bank.app.service.AccountService;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.bank.app.dao.AccountDAO;
import com.bank.app.model.Account;
import com.bank.app.security.keyStore.KeyStoreManager;
import com.bank.app.security.symmetricEncryption.AESUtil;

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

}
