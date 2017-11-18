package main;

import java.io.*;
import java.net.Socket;

import protocol.Message;

public class ClientHandler implements Runnable{	
	Socket socket;
	BufferedReader input;
	PrintWriter output;
	
	
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	public ClientHandler(Socket socket){
		this.socket = socket;
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("A client handler has just been created");
	}
	
	public void run() {
		String[] contents = {"hello", "this", "is", "test"};
		Message message = new Message("message", contents);
		try {
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run2() {
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
