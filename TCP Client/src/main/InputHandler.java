package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import protocol.Message;

public class InputHandler implements Runnable {

	Socket socket;

	ObjectInputStream ois;

	public InputHandler(Socket socket) {
		this.socket = socket;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		Message message;
		String contents;
		Object data;
		while (true) {
			try {
				message = (Message) ois.readObject();
				contents = (String) message.getContents();
				data = message.getData();
				switch (contents) {

				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
