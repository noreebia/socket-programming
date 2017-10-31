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
			packet = new DatagramPacket(buf, buf.length);
			try {
				socket.receive(packet);
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
			try {
				temp = (Data) is.readObject();
				dataController.cloneData(temp);
				System.out.println("data received and cloned");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch(Exception e) {
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
