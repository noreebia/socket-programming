package main;

import java.io.*;
import java.net.Socket;

public class Client {

	Socket socket;
	DataInputStream inputStream;
	DataOutputStream outputStream;
	
	PrintWriter writer;
	BufferedReader reader;
	
	public Client() {
		try {
			socket = new Socket("localhost", 40000);
			//inputStream = new DataInputStream(socket.getInputStream());
			//outputStream = new DataOutputStream(socket.getOutputStream());
			
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("connection established");
		
		int count = 0;
		while(true) {
			try{
				count++;
				//outputStream.writeInt(count);
				writer.println(count);
				System.out.println("sent " + count + " to server");	
			}catch(Exception e){
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
