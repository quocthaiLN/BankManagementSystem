package com.bank.app.controller;

import com.bank.app.model.Permission;
import com.bank.app.model.PermissionNames;
import com.bank.app.service.AccountService.AccountService;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.util.*;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
//import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class bankServer {
	private int port = 9999;
	private boolean isServerDone = false;

	// Khoi chay server tai port 9999 va dung phuong thuc run de chay
	public static void main(String[] args) {
		bankServer server = new bankServer();
		int p = server.getPort();
		server.setPort(p);
		server.run();
	}

	// Khoi tao mac dinh
	bankServer() {

	}

	// Khoi tao co tham so
	bankServer(int port) {
		this.port = port;
	}

	// Getter, Setter
	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	// Tao va khoi tao SSLContext
	private SSLContext createSSLContext() {
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS");
			keyStore.load(new FileInputStream("server.jks"), "bankqab".toCharArray());

			// Tao quan ly khoa
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
			keyManagerFactory.init(keyStore, "bankqab".toCharArray());
			KeyManager[] km = keyManagerFactory.getKeyManagers();

			// Tao quan ly tin cay
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
			trustManagerFactory.init(keyStore);
			TrustManager[] tm = trustManagerFactory.getTrustManagers();

			// Tao SSLContext
			SSLContext sslContext = SSLContext.getInstance("TLSv1");
			sslContext.init(km, tm, null);

			return sslContext;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	// Phuong thuc run
	public void run() {
		SSLContext sslContext = this.createSSLContext();

		try {
			// Tạo nhà máy socket server
			SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();

			// Tạo socket server
			SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(this.port);

			System.out.println("May chu da khoi tao");
			while (!isServerDone) {
				SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();

				// Bắt đầu luồng máy chủ
				new ServerThread(sslSocket).start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Luồng xử lý socket từ máy khách
	static class ServerThread extends Thread {
		private SSLSocket sslSocket = null;

		ServerThread(SSLSocket sslSocket) {
			this.sslSocket = sslSocket;
		}

		// Trả về <Permission, response>
		public Map<Permission, String> handleRequest(String request) {
			HashMap<Permission, String> map = new HashMap<>();
			Permission perm = null;
			String response = "success";

			String command = request.toUpperCase();

			switch (command) {
				case "WITHDRAW_FUNDS":
					perm = new Permission(PermissionNames.getWithdrawFunds());
					// logic rut tiền
					break;
				case "VIEW_ALL_CUSTOMER":
					perm = new Permission(PermissionNames.getViewAllCustomer());
					break;
				case "ADD_CUSTOMER":
					perm = new Permission(PermissionNames.getAddCustomer());
					break;
				case "DELETE_ACCOUNT":
					perm = new Permission(PermissionNames.getDeleteAccount());
					break;
				case "CREATE_ACCOUNT":
					perm = new Permission(PermissionNames.getCreateAccount());
					break;
				case "VIEW_CUSTOMER_INFO":
					perm = new Permission(PermissionNames.getViewCustomer());
					break;
				case "VIEW_ACCOUNT_INFO":
					perm = new Permission(PermissionNames.getViewAccount());
					break;
				case "DEPOSIT_FUNDS":
					perm = new Permission(PermissionNames.getDepositFunds());
					break;
				case "TRANSFER_FUNDS":
					perm = new Permission(PermissionNames.getTransferFunds());
					break;
				case "VIEW_TRANSACTION_HISTORY":
					perm = new Permission(PermissionNames.getViewTransactionHistory());
					break;
				case "ADD_EMPLOYEE":
					perm = new Permission(PermissionNames.getAddEmployee());
					break;
				case "VIEW_EMPLOYEE_INFO":
					perm = new Permission(PermissionNames.getViewEmployee());
					break;
				case "VIEW_ALL_USER_ACCOUNT":
					perm = new Permission(PermissionNames.getViewAllUserAccount());
					break;
				case "VIEW_ALL_EMPLOYEE":
					perm = new Permission(PermissionNames.getViewAllEmployee());
					break;
				default:
					perm = new Permission("UNKNOWN COMMAND");
					response = "failed";
			}

			map.put(perm, response);

			return map;
		}

		@Override
		public void run() {

			try {
				// Bat tay
				sslSocket.startHandshake();

				// Bat dau xu ly
				InputStream input = sslSocket.getInputStream();
				OutputStream output = sslSocket.getOutputStream();

				BufferedReader read = new BufferedReader(new InputStreamReader(input));
				PrintWriter write = new PrintWriter(new OutputStreamWriter(output), true);
				String line = null;
				while ((line = read.readLine()) != null) {

					System.out.println("Client: " + line);
					if (line.trim().equalsIgnoreCase("stop")) {
						break;
					}

					Map<Permission, String> ans = handleRequest(line);
					String respond = "failed";
					if(ans != null && !ans.isEmpty()) {
						// Lấy request đầu
						for(Permission perms : ans.keySet()) {
							respond = perms.getPermissionName() + " " + ans.get(perms);
							break;
						}
					}

					System.out.println("Server response to client: " + respond);

 					write.println(respond);
				}
				sslSocket.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}
}
