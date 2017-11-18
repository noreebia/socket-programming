package main;

import java.net.Socket;

public class Client {
	Socket socket;
	String nickname;
	
	public Client(Socket socket, String nickname) {
		this.socket = socket;
		this.nickname = nickname;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public String getNickname(){
		return nickname;
	}
}
