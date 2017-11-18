package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	
	ArrayList<Client> clients = new ArrayList<Client>();
	ArrayList<Thread> clientHandlingThreads = new ArrayList<Thread>();
	ServerSocket serverSocket;
		
	BufferedReader input;
	PrintWriter output;
	
	public Server() throws IOException {
		serverSocket = new ServerSocket(40000);
		
		String userSelectedNickname;
		while(true) {
			System.out.println("Listening for clients...");
			Socket socket = serverSocket.accept();
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			
			userSelectedNickname = input.readLine();
			
			if(isNicknameDuplicate(userSelectedNickname)) {
				output.println("duplicate nickname");
				System.out.println("duplicate nickname");
			}
			else{
				output.println("connection successful");
				clients.add(new Client(socket, userSelectedNickname));
				new Thread(new ClientHandler(socket)).start();
			}
			
			System.out.println("current connected clients: " + clients.size());
			//new Thread(new ClientHandler(socket)).start();
		}
		
		/*
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream(), true);
		
		String message = input.readLine();
		System.out.println(message);
		output.println(message + ":) <- added from server");
		*/ 
	}
	
	public boolean isNicknameDuplicate(String nickname) {
		for(Client c:clients) {
			if(c.getNickname().equals(nickname)) {
				return true;
			}
		}
		return false;
	}
}
