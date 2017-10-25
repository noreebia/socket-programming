import java.io.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;

import model.*;

public class Server {
	DatagramSocket listeningSocket;
	DatagramSocket ioSocket;
	byte[] buf = new byte[8192];
	InetAddress clientAddress;
	int clientPort;

	ByteArrayInputStream bais;
	ObjectInputStream is;

	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	ObjectOutputStream os;

	DatagramPacket packet;

	Random rand = new Random();

	int pos[] = {1000, 700};

	Data data = new Data();

	Thread inputHandler;
	Thread outputHandler;

	ArrayList<Client> clients = new ArrayList<Client>();
	
	int connectionCount=0;

	public Server() {
		try {
			os = new ObjectOutputStream(baos);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			listeningSocket = new DatagramSocket(50000);
			ioSocket = new DatagramSocket(50001);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		packet = new DatagramPacket(buf, buf.length);

		System.out.println("listening...");
		try {
			listeningSocket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("received packet from client");

		clientAddress = packet.getAddress();
		clientPort = packet.getPort();

		connectionCount++;
		buf = intToByteArray(connectionCount);
		
		packet = new DatagramPacket(buf, buf.length, clientAddress, clientPort);
		try {
			listeningSocket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		addClient(connectionCount, clientAddress, clientPort);
		
		inputHandler = new Thread(new InputHandlingThread(ioSocket, data));
		outputHandler = new Thread(new OutputHandlingThread(ioSocket, data, clients));

		inputHandler.start();
		outputHandler.start();
	}
	
	public byte[] intToByteArray(int i){
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putInt(i);
		return bb.array();
	}

	public void run() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		inputHandler.interrupt();
		outputHandler.interrupt();
		
		while (true) {
			if(inputHandler.isInterrupted() || outputHandler.isInterrupted()) {
				break;
			}
		}
		System.out.println("Exited");
	}

	public void addClient(int id, InetAddress clientAddress, int clientPort) {
		clients.add(new Client(id, clientAddress, clientPort));
	}

}
