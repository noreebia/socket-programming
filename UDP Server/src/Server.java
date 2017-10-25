import java.io.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.*;

public class Server {
	DatagramSocket socket;
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

	public Server() {
		try {
			os = new ObjectOutputStream(baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			socket = new DatagramSocket(50000);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		packet = new DatagramPacket(buf, buf.length);

		System.out.println("listening...");
		try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("received packet from client");

		clientAddress = packet.getAddress();
		clientPort = packet.getPort();

		/*
		 * try { os.writeObject(pos); } catch (IOException e) { e.printStackTrace(); }
		 * buf = baos.toByteArray();
		 * 
		 * packet = new DatagramPacket(buf, buf.length, clientAddress, clientPort);
		 * 
		 * try { socket.send(packet); } catch (IOException e) { e.printStackTrace(); }
		 * System.out.println("Sent");
		 */
		while (true) {
			//pos[0] = rand.nextInt(1200) + 1;
			//pos[1] = rand.nextInt(800) + 1;
			pos[0]--;
			pos[1]--;
			if(pos[0] < 0 ) {
				pos[0] = 1200;
			}
			if(pos[1] < 0) {
				pos[1] = 800;
			}
			System.out.println(pos[0] + ", " + pos[1]);
			try {
				//baos = new ByteArrayOutputStream();
				baos.reset();
				os = new ObjectOutputStream(baos);
				os.writeObject(pos);
			} catch (IOException e) {
				e.printStackTrace();
			}
			buf = baos.toByteArray();

			packet = new DatagramPacket(buf, buf.length, clientAddress, clientPort);

			try {
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("Sent");
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
