package main;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Client {
	Socket socket;

	BufferedReader input;
	PrintWriter output;

	Scanner scanner = new Scanner(System.in);

	public Client() throws Exception {
		socket = new Socket("localhost", 40000);
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream(), true);

		while (true) {
			System.out.print("Enter message: ");
			String userInput = scanner.nextLine();
			if (userInput.equals("/quit")) {
				break;
			}
			output.println(userInput);
			String message = input.readLine();
			System.out.println("From Server: " + message);
		}
		socket.close();
	}
}
