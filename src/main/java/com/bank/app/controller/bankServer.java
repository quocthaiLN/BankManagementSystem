package com.bank.app.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.util.Scanner;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
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

		public void run() {

			try {
				// Bat tay
				sslSocket.startHandshake();

				// Bat dau xu ly
				InputStream input = sslSocket.getInputStream();
				OutputStream output = sslSocket.getOutputStream();

				BufferedReader read = new BufferedReader(new InputStreamReader(input));
				PrintWriter write = new PrintWriter(new OutputStreamWriter(output), true);
				Scanner scan = new Scanner(System.in);
				String line = null;
				while ((line = read.readLine()) != null) {

					System.out.println("Client: " + line);
					System.out.print("Server: ");
					String respond = scan.nextLine();
					write.println(respond);
					if (line.trim().equalsIgnoreCase("stop")) {
						break;
					}
				}
				scan.close();
				sslSocket.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}
}
