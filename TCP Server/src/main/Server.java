package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	ServerSocket serverSocket;
	Socket socket;
	BufferedReader reader;
	PrintWriter writer;
	
	public Server() {
		try {
			serverSocket = new ServerSocket(40000);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("listening...");
		try {
			socket = serverSocket.accept();
			//inputStream = new DataInputStream(socket.getInputStream());
			//outputStream = new DataOutputStream(socket.getOutputStream());
			writer = new PrintWriter(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()) );
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("about to read message from client");
		while(true) {
			try {
				System.out.println(reader.readLine());
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
