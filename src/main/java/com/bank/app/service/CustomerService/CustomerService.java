package com.bank.app.service.CustomerService;

import com.bank.app.model.Customer;
import com.bank.app.dao.CustomerDAO;
import com.bank.app.security.*;

import java.security.KeyStore;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import io.github.cdimascio.dotenv.Dotenv;

import com.bank.app.security.keyStore.*;

import com.bank.app.security.symmetricEncryption.*;

public class CustomerService implements CustomerServiceImpl {
    private CustomerDAO customerDAO = null;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
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
}
