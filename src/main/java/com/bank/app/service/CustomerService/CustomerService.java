package com.bank.app.service.CustomerService;

import com.bank.app.dao.AccountDAO;
import com.bank.app.model.Account;
import com.bank.app.model.Customer;
import com.bank.app.dao.CustomerDAO;
import com.bank.app.security.hash.*;
import com.bank.app.security.keyStore.*;
import com.bank.app.security.symmetricEncryption.*;

import java.security.KeyStore;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import io.github.cdimascio.dotenv.Dotenv;

import com.bank.app.security.keyStore.*;

import com.bank.app.security.symmetricEncryption.*;

public class CustomerService implements CustomerServiceImpl {
    private CustomerDAO customerDAO = null;
    private AccountDAO accountDAO = null;


    public CustomerService(CustomerDAO customerDAO, AccountDAO accountDAO) {
        this.customerDAO = customerDAO;
        this.accountDAO = accountDAO;
    }

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
        this.accountDAO = new AccountDAO();
    }

    @Override
    public boolean addCustomer(Customer infoCustomer) {
        if (infoCustomer == null)
            return false;

        // Đối tượng chứa thông tin đã mã hóa
        Customer encryptedInfoCustomer = new Customer();

        try {

            // Tạo secret key dành cho AES
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey secretKey = keyGen.generateKey();

            // Tạo Alias bằng CustomerID
            String entryAlias = String.valueOf(infoCustomer.getCustomerID());

            // Lưu khóa bí mật vào KeyStore
            KeyStoreManager keyStoreManager = new KeyStoreManager();
            keyStoreManager.addEntry(entryAlias, secretKey);

            // Tiến hành mã hóa một số trường nhạy cảm
            encryptedInfoCustomer.setCustomerID(infoCustomer.getCustomerID());
            encryptedInfoCustomer.setName(AESUtil.encrypt(infoCustomer.getName(), secretKey));
            encryptedInfoCustomer.setBirthDate(infoCustomer.getBirthDate());
            encryptedInfoCustomer.setGender(infoCustomer.getGender());
            encryptedInfoCustomer
                    .setIdentityNumber(AESUtil.encrypt(infoCustomer.getIndentityNumber(), secretKey));
            encryptedInfoCustomer.setPhone(AESUtil.encrypt(infoCustomer.getPhone(), secretKey));
            encryptedInfoCustomer.setAddress(AESUtil.encrypt(infoCustomer.getAddress(), secretKey));
            encryptedInfoCustomer.setEmail(AESUtil.encrypt(infoCustomer.getEmail(), secretKey));
            encryptedInfoCustomer.setType(infoCustomer.getType());
            encryptedInfoCustomer.setStatus(infoCustomer.getStatus());
            encryptedInfoCustomer.setRegisterDate((infoCustomer.getRegisterDate()));
            // Thêm vào csdl bằng DAO
            customerDAO.insert(encryptedInfoCustomer);
            System.out.println("Customer's infomation has been added.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;

    }

    @Override
    public Customer getCustomer(String customerID) {

        try {
            // Tạo đối tượng KeyStoreManager
            KeyStoreManager keyStoreManager = new KeyStoreManager();

            // Lấy secret key
            SecretKey secretKey = keyStoreManager.loadEntry(String.valueOf(customerID));

            // Lấy thông tin customer
            Customer infoCustomer = customerDAO.getCustomerByID(customerID);

            // Decypt các thông tin
            infoCustomer.setName(AESUtil.decrypt(infoCustomer.getName(), secretKey));
            infoCustomer.setIdentityNumber(AESUtil.decrypt(infoCustomer.getIndentityNumber(), secretKey));
            infoCustomer.setPhone(AESUtil.decrypt(infoCustomer.getPhone(), secretKey));
            infoCustomer.setAddress(AESUtil.decrypt(infoCustomer.getAddress(), secretKey));
            infoCustomer.setEmail(AESUtil.decrypt(infoCustomer.getEmail(), secretKey));
            System.out.println("Customer's information has been loaded.");
            return infoCustomer;
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> showAccount(String customerID) {
        return accountDAO.getAccountsByCustomerID(customerID);
    }
}
