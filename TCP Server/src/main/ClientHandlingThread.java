package main;

import java.io.*;
import java.net.Socket;

public class ClientHandlingThread implements Runnable {

	Socket socket;
	BufferedReader reader;
	PrintWriter writer;

	public ClientHandlingThread(Socket socket) {
		this.socket = socket;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			try {
				System.out.println(reader.readLine());
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
