package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	ServerSocket serverSocket;
		
	BufferedReader input;
	PrintWriter output;
	
	public Server() throws IOException {
		serverSocket = new ServerSocket(40000);
		while(true) {
			Socket socket = serverSocket.accept();
			new Thread(new ClientHandler(socket)).start();
		}
		
		/*
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream(), true);
		
		String message = input.readLine();
		System.out.println(message);
		output.println(message + ":) <- added from server");
		*/ 
	}
}
