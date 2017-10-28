import java.io.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.*;

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
	
	ExecutorService executor = Executors.newCachedThreadPool();
	ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
	
	long startTime;
	public Server() {
		startTime = System.currentTimeMillis();
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
		
		executor.execute(new InputHandlingThread(ioSocket, data));
		ses.scheduleWithFixedDelay(new OutputHandlingThread(ioSocket, data, clients), 0, 8, TimeUnit.MILLISECONDS);
		
		
		/*
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
		*/
	}

	public void run() {
		
		while(true) {
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
		}
		
		/*
		while(true) {
			if(System.currentTimeMillis() - startTime >= 10000) {
				executor.shutdown();
				ses.shutdownNow();

				try {
					ses.awaitTermination(5000, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Server Shutting down");
				System.exit(1);
			}
			
			System.out.println(System.currentTimeMillis() - startTime);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		*/
	}
	
	public byte[] intToByteArray(int i){
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putInt(i);
		return bb.array();
	}

	public void addClient(int id, InetAddress clientAddress, int clientPort) {
		clients.add(new Client(id, clientAddress, clientPort));
	}

}
