package com.bank.app.controller;

import com.bank.app.model.Permission;
import com.bank.app.model.PermissionNames;

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

        Boolean running = true;
        while(running){
            sendRequest(scan, out, running);
            String response = in.readLine();
            System.out.println("Server tra loi: " + response);
        }

        scan.close();

        socket.close();
    }
    public static void menu(){
        System.out.println("Danh sach lua chon");
        System.out.println("0. Exit");
        System.out.println("1. Add Customer");
        System.out.println("2. Delete Account");
        System.out.println("3. View Customer Info");
        System.out.println("4. Create Account");
        System.out.println("5. View Account Info");
        System.out.println("6. Deposit Funds");
        System.out.println("7. Withdraw Funds");
        System.out.println("8. Transfer Funds");
        System.out.println("9. View Transaction History");
        System.out.println("10. Add Employee");
        System.out.println("11. View Employee Info");
        System.out.println("12. View All User Accounts");
        System.out.println("13. View All Customers");
        System.out.println("14. View All Employees");
    }
    public static void sendRequest(Scanner scan, PrintWriter out, Boolean running){
        PermissionNames pNames = new PermissionNames();
        menu();
        System.out.println("Vui long nhap vao luu chon cua ban: ");
        int choice = scan.nextInt();
        switch (choice) {
            case 1: {
                String message = pNames.getAddCustomer();
                out.println(message);
                break;
            }
            case 2: {
                String message = pNames.getDeleteAccount();
                out.println(message);
                break;
            }
            case 3: {
                String message = pNames.getViewCustomer();
                out.println(message);
                break;
            }
            case 4: {
                String message = pNames.getCreateAccount();
                out.println(message);
                break;
            }
            case 5: {
                String message = pNames.getViewAccount();
                out.println(message);
                break;
            }
            case 6: {
                String message = pNames.getDepositFunds();
                out.println(message);
                break;
            }
            case 7: {
                String message = pNames.getWithdrawFunds();
                out.println(message);
                break;
            }
            case 8: {
                String message = pNames.getTransferFunds();
                out.println(message);
                break;
            }
            case 9: {
                String message = pNames.getViewTransactionHistory();
                out.println(message);
                break;
            }
            case 10: {
                String message = pNames.getAddEmployee();
                out.println(message);
                break;
            }
            case 11: {
                String message = pNames.getViewEmployee();
                out.println(message);
                break;
            }
            case 12: {
                String message = pNames.getViewAllUserAccount();
                out.println(message);
                break;
            }
            case 13: {
                String message = pNames.getViewAllCustomer();
                out.println(message);
                break;
            }
            case 14: {
                String message = pNames.getViewAllEmployee();
                out.println(message);
                break;
            }
            case 0: {
                out.println("stop"); // gửi tín hiệu dừng
                running = false;
                break;
            }
            default:
                System.out.println("Khong hop le!");
        }
    }
}
