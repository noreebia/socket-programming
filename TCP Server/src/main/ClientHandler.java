package main;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
	Socket socket;
	BufferedReader input;
	PrintWriter output;
	
	public ClientHandler(Socket socket){
		this.socket = socket;
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("A client handler has just been created");
	}

	public void run() {
		String message;
		while(true) {
			try {
				System.out.println("listening for client input...");
				message = input.readLine();
				System.out.println("From client: " + message);
				output.println(message + " <- echo from server");
				/*
				if(message != null) {
					System.out.println("From client: " + message);
					output.println(message + " <- echo from server");
				}
				*/
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
