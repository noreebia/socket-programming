
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import client_exclusive.User;
import control.DataController;
import model.Data;
import model.Player;

public class InputHandlingThread implements Runnable {

	byte[] buf = new byte[20000];

	DatagramPacket packet;
	DatagramSocket socket;

	ByteArrayInputStream bais;
	ObjectInputStream is;

	Data temp;
	DataController dataController;

	short connectionID;
	User user;

	public InputHandlingThread(DatagramSocket socket, DataController dataController, short connectionID, User user) {
		System.out.println("Input handler created.");
		this.socket = socket;
		this.dataController = dataController;
		this.connectionID = connectionID;
		this.user = user;
	}

	public void run() {
		while (true) {
			try {
				packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);

				System.out.println("Received packet");
				bais = new ByteArrayInputStream(packet.getData());
				is = new ObjectInputStream(bais);

				try {
					temp = (Data) is.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					System.exit(1);
				}
				dataController.updateData(temp);
				System.out.println("data received and cloned");

				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
