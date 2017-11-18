package main;

import java.net.*;
import java.util.Scanner;

import protocol.Message;

import java.io.*;

public class Client {
	Socket socket;

	BufferedReader input;
	PrintWriter output;

	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	Scanner scanner = new Scanner(System.in);

	public Client() throws Exception {
		socket = new Socket("localhost", 40000);

		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream(), true);
		
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());

		
		/*
		Message message = (Message)ois.readObject();
		System.out.println(message.getMessage());
		
		String[] messageContents = (String[]) message.getContents();
		System.out.println(messageContents[0]);
		System.out.println(messageContents[1]);
		System.out.println(messageContents[2]);
		System.out.println(messageContents[3]);
		*/
		
		/*
		while (true) {
			try{
				System.out.print("Enter message: ");
				String userInput = scanner.nextLine();
				if (userInput.equals("/quit")) {
					break;
				}
				output.println(userInput);
				String message = input.readLine();
				System.out.println("From Server: " + message);
			}catch(SocketException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		*/
		socket.close();
	}
}
