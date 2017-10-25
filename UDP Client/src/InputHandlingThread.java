import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import model.Data;

public class InputHandlingThread implements Runnable{

	int[] pos;
	byte[] buf = new byte[8192];

	DatagramPacket packet;
	DatagramSocket socket;
	ByteArrayInputStream bais;
	ObjectInputStream is;
	
	Data data, temp;
	
	public InputHandlingThread(DatagramSocket socket, Data data) {
		System.out.println("Input handler created.");
		this.socket = socket;
		this.data = data;
	}
	
	public void run() {
		while(true) {
			System.out.println("Receiving");
			packet = new DatagramPacket(buf,buf.length);
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
				temp = (Data)is.readObject();
				data.clone(temp);
				System.out.println("data received and cloned");
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
			
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}	
}
