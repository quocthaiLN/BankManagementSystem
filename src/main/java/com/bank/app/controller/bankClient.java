package com.bank.app.controller;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;
import java.util.Scanner;

public class bankClient {
    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 9999;
        String truststoreFile = "clienttrust.jks";
        char[] password = "bankqab".toCharArray();
        Scanner scan = new Scanner(System.in);

        // Load truststore
        KeyStore ts = KeyStore.getInstance("JKS");
        ts.load(new FileInputStream(truststoreFile), password);

        // Init trust manager
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ts);

        // Init SSL context
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

        // Create SSL socket
        SSLSocketFactory sf = sslContext.getSocketFactory();
        SSLSocket socket = (SSLSocket) sf.createSocket(host, port);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.print("Nhap vao thong tin gui den server(stop de dung): ");
        String message = scan.nextLine();
        while(!message.equalsIgnoreCase("stop")){
            out.println(message);
            String response = in.readLine();
            System.out.println("Server tra loi: " + response);
            System.out.print("Nhap vao thong tin gui den server(stop de dung): ");
            message = scan.nextLine();
        }
        scan.close();

        socket.close();
    }
}
