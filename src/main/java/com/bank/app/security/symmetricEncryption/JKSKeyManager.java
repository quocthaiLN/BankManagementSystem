package com.bank.app.security.symmetricEncryption;

import javax.crypto.SecretKey;
// package javax.cpyto: cup cấp class, interface cho mã hóa đối xứng
// class SecretKey: interface đại diện cho 1 khóa bí mật, 
import java.io.FileInputStream;
// package cấp các class để đọc/ghi dữ liệu vào/ra từ file, mạng, bộ nhớ,...
import java.security.KeyStore;
// class KeyStore: dùng để lưu trữ SecretKey, load từ file .jks, truy suất key

public class JKSKeyManager {

    // ! 4 biến này nên lưu trong môi trường gì đó
    private static final String KEYSTORE_PATH = "config/mykeystore.jks"; // đường dẫn đến file .jks chứa khóa
    private static final String STORE_PASSWORD = "changeit"; // mật khẩu mở file .jks
    private static final String KEY_ALIAS = "aeskey"; // alias của khóa AES
    private static final String KEY_PASSWORD = "changeit"; // mật khẩu để lấy khóa tương ứng với alias

    // ! Đặt throw ở đây có nghĩa là nếu ngoại lệ xảy ra sẽ cho hàm bên ngoài xử lí
    public static SecretKey loadAESKey() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JCEKS"); // tạo đối tượng kiểu JCEKS

        // tại đối tượng kiểu FileInputStream để đọc mật khẩu
        try (FileInputStream fis = new FileInputStream(KEYSTORE_PATH/* đường dẫn đến .jks */)) {
            keyStore.load(fis, STORE_PASSWORD.toCharArray()); // load(): nạp key vào bộ nhớ
                                                              // toCharArray(): do load() chỉ nhận char[]
        }

        // tạo đối tương kiểu ProtectionParameter dùng để cung cấp mật khẩu riêng cho
        // từng alias
        KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(KEY_PASSWORD.toCharArray());

        // entry là một đối tương để lưu key
        KeyStore.SecretKeyEntry entry = (KeyStore.SecretKeyEntry) keyStore.getEntry(KEY_ALIAS, protParam);
        // lấy ra 1 entry tương ứng với alias với alias = KEY_ALIAS, protParam là mật
        // khẩu
        // ép kiểu về SecretKeyEntry để tương ứng với AES

        // trả về
        return entry.getSecretKey();

    }

}

// ! làm sao để lưu và sử dụng store pass và key pass một cách an toàn ==> mình
// ! chọn cách sử dụng biến môi trường