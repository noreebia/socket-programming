package model;

import java.net.InetAddress;

public class Client {
	
	int id;
	
	InetAddress address;
	int port;
	
	public Client(InetAddress address, int port) {
		this.address = address;
		this.port = port;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public InetAddress getAddress() {
		return address;
	}
	
	public int getPort() {
		return port;
	}

}
