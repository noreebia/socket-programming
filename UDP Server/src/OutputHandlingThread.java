import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;

import control.DataController;
import model.*;

public class OutputHandlingThread implements Runnable{
		
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	ObjectOutputStream os;

	byte[] buf = new byte[8192];
	DatagramSocket ioSocket;
	DatagramPacket packet;
	
	DataController dataController;
	ArrayList<Client> clients = new ArrayList<Client>();
	
	public OutputHandlingThread(DatagramSocket ioSocket, DataController dataController, ArrayList<Client> clients) {
		System.out.println("OutputHandlingThread created");
		this.ioSocket = ioSocket;
		try {
			os = new ObjectOutputStream(baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		this.clients = clients;
		this.dataController = dataController;
	}

	public void run() {
		try {
			baos.reset();
			os = new ObjectOutputStream(baos);
			os.writeObject(dataController.getData());
		} catch (IOException e) {
			e.printStackTrace();
		}
		buf = baos.toByteArray();
	

		for(Client c:clients) {
			packet = new DatagramPacket(buf, buf.length, c.getAddress(), c.getPort());
			try {
				ioSocket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Sent to:" + c.getAddress().toString());
		}
		System.out.println("Length of sent data in byte: " + buf.length);
	}
}
