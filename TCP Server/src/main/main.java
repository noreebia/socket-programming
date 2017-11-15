package main;

import java.io.*;
import java.net.*;

public class Main {
	public static void main(String argv[]) throws Exception {
		Server server = new Server();
		
		/*
		ServerSocket serverSocket = new ServerSocket(40000);
		Socket socket = new Socket();
		
		socket = serverSocket.accept();
		BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
		
		String message = inputFromClient.readLine();
		System.out.println("Received: " + message);
		outputToClient.writeBytes(message + ":) <- added by server\n");
		*/
		/*
		while(true) {
			clientSocket = serverSocket.accept();
			BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			DataOutputStream outputToClient = new DataOutputStream(clientSocket.getOutputStream());
			
			String message = inputFromClient.readLine();
			System.out.println("Received: " + message);
		}
		*/
	}
}