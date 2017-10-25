import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import model.Player;

public class OutputHandlingThread implements Runnable {

	InetAddress serverAddress;
	int serverPort;
	
	DatagramSocket socket;
	DatagramPacket packet;

	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	ObjectOutputStream os;
	Player player;
	
	byte[] buf = new byte[8192];

	public OutputHandlingThread(DatagramSocket socket, InetAddress serverAddress, int serverPort, Player player) {
		System.out.println("Output handler created");
		this.serverAddress = serverAddress;
		this.socket = socket;
		this.player = player;
		this.serverPort = serverPort;
		
		try {
			os = new ObjectOutputStream(baos);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void run() {
		while(true) {
			System.out.println("Sending");
			
			try {
				baos.reset();
				os = new ObjectOutputStream(baos);
				os.writeObject(player);
			} catch (IOException e) {
				e.printStackTrace();
			}
			buf = baos.toByteArray();

			packet = new DatagramPacket(buf, buf.length, serverAddress, serverPort);

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

		/*
		System.out.println("Received packet");
		bais = new ByteArrayInputStream(packet.getData());

		try {
			is = new ObjectInputStream(bais);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			temp = (int[]) is.readObject();
			System.arraycopy(temp, 0, pos, 0, temp.length);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(pos[0] + ", " + pos[1]);
		*/
	}
}
