import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

import model.*;

public class InputHandlingThread implements Runnable{

	DatagramSocket ioSocket;
	DatagramPacket packet;
	byte[] buf = new byte[8192];
	Player temp;
	Data data;
	
	ByteArrayInputStream bais;
	ObjectInputStream is;

	public InputHandlingThread(DatagramSocket ioSocket, Data data) {
		System.out.println("Input handler created.");
		this.ioSocket = ioSocket;
		this.data = data;
	}
	
	public void run() {
		while(true) {
			System.out.println("Receiving");
			packet = new DatagramPacket(buf,buf.length);
			try {
				ioSocket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("Received packet");
			bais = new ByteArrayInputStream(packet.getData());
			
			try {
				is = new ObjectInputStream(bais);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("attempting to update data");
			System.out.println("current num of players: " + data.getPlayers().size());
			try {
				temp = (Player)is.readObject();
				data.updatePlayer(temp);
				System.out.println("received player object from client and data updated");
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
		}
	}

}
