package com.bank.app.connection;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

// Về việc tạo SERVER_KEYSTORE, SERVER_TRUSTSTORE ta dùng key tool của JVM cung cấp
// Khi tạo thì nó đã tạo luôn private key, cert 
public class KeyStoreLoader {

    // Đường dẫn tới keystore và truststore
    // keystore này để lưu private key và cert của chính server
    // truststore này để lưu các cert mà nó tin tưởng, ở đây là cert của client
    // cert để làm gì? --> để xác định public key nhận được có đáng tin hay ko
    private static final String SERVER_KEYSTORE_PATH = "src/main/resources/key/ServerKeystore.jks";
    // private static final String SERVER_TRUSTSTORE_PATH =
    // "src/main/resources/key/ServerTruststore.jks";

    // Tên biến trong .env
    private static final String SERVER_KEYSTORE_PASSWORD_NAME = "SERVER_KEYSTORE_PASSWORD";
    // private static final String SERVER_TRUSTSTORE_PASSWORD_NAME =
    // "SERVER_TRUSTSTORE_PASSWORD";

    public SSLContext createSSLContext() throws Exception {
        // Load password từ file .env
        Dotenv dotenv = Dotenv.configure()
                .directory("src/main/resources/key")
                .filename(".env")
                .load();

        // Lúc này đã có khóa của 2 store
        char[] keyStorePwd = dotenv.get(SERVER_KEYSTORE_PASSWORD_NAME).toCharArray();
        // char[] trustStorePwd =
        // dotenv.get(SERVER_TRUSTSTORE_PASSWORD_NAME).toCharArray();

        // Load Server Keystore (chứa private key + cert server)
        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (FileInputStream fis = new FileInputStream(SERVER_KEYSTORE_PATH)) {
            keyStore.load(fis, keyStorePwd);
        }

        // Khởi tạo KeyManager từ Keystore, cái này nó làm mẹ gì? --> Vì SSLContext nhận
        // nó làm đầu vào, không truyền trực tiếp private key và cert
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, keyStorePwd);

        // Load Server Truststore (chứa cert client)
        // KeyStore trustStore = KeyStore.getInstance("JKS");
        // try (FileInputStream fis = new FileInputStream(SERVER_TRUSTSTORE_PATH)) {
        // trustStore.load(fis, trustStorePwd);
        // }

        // !Update: Tui chỉ định làm 1 chiều, tức server sẽ tiếp nhận kết nối mặc kệ có
        // !đáng tin cậy hay không nên ta bỏ phần này nhé.

        // Khởi tạo TrustManager từ TrustStore, cái này nó làm mẹ gì? --> Vì SSLContext
        // nhận nó làm đầu vào, không truyền trực tiếp cert
        // TrustManagerFactory tmf =
        // TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        // tmf.init(trustStore);

        // Tạo SSLContext (TLS)
        // SSLContext sẽ giúp ta xử lí, giải mã, mã hóa (private key và public key),
        // cert trong lúc trao đổi server-client mọi thứ nó lo hết mình chỉ cần private
        // key và cert server và cert client để nó làm
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), /* tmf.getTrustManagers() */ null, null);

        return sslContext;
    }

}
