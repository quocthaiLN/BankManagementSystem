package com.bank.app.security.keyStore;

import java.security.KeyStore;
import io.github.cdimascio.dotenv.*;
import java.io.FileOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import javax.crypto.SecretKey;

public class KeyStoreManager {
    
    static private String KEYSTORE_PATH = "src\\main\\resources\\key\\KeyStore.jceks";
    static private String KEYSTORE_PASSWORD_NAME = "KEYSTORE_PASSWORD";

    public boolean initKeyStore(String keyStorePassword) {
        try {
            File file = new File(KEYSTORE_PATH);
            if (file.exists()) {
                System.out.println("Keystore already exists: " + file.getName());
                return true;
            }

            KeyStore keyStore = KeyStore.getInstance("JCEKS");
            keyStore.load(null, keyStorePassword.toCharArray());

            try (FileOutputStream fos = new FileOutputStream(file)) {
                keyStore.store(fos, keyStorePassword.toCharArray());
            }

            System.out.println("New keystore created successfully.");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addEntry(String aliasEntry, SecretKey secretKey) {

        try {
            Dotenv dotenv = Dotenv.load();
            char[] pwd = dotenv.get(KEYSTORE_PASSWORD_NAME).toCharArray();
            KeyStore keyStore = KeyStore.getInstance("JCEKS");
            keyStore.load(new FileInputStream(KEYSTORE_PATH), pwd);

            KeyStore.SecretKeyEntry secret = new KeyStore.SecretKeyEntry(secretKey);
            KeyStore.ProtectionParameter password = new KeyStore.PasswordProtection(pwd);
            keyStore.setEntry(aliasEntry, secret, password);

            keyStore.store(new FileOutputStream(KEYSTORE_PATH), pwd);

            System.out.println("Store entry successffully");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public SecretKey loadEntry(String aliasEntry) {
        try {
            Dotenv dotenv = Dotenv.load();
            char[] pwd = dotenv.get(KEYSTORE_PASSWORD_NAME).toCharArray();
            KeyStore keyStore = KeyStore.getInstance("JCEKS");

            keyStore.load(new FileInputStream(KEYSTORE_PATH), pwd);

            KeyStore.ProtectionParameter loadParam = new KeyStore.PasswordProtection(pwd);
            KeyStore.Entry entry = keyStore.getEntry(aliasEntry, loadParam);

            if (entry instanceof KeyStore.SecretKeyEntry) {
                return ((KeyStore.SecretKeyEntry) entry).getSecretKey();
            } else {
                System.err.println("Entry is not a SecretKeyEntry");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}