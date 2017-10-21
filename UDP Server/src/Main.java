import java.io.*;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.net.*;

public class Main {
	static DatagramSocket socket;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		try {
			socket = new DatagramSocket(50000);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		byte[] buf = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		
		ArrayList<Integer> array = null;

		ByteArrayInputStream byteInputStream;
		ObjectInputStream inputStream;
		
		int count = 0;
		String messageFromClient;
		long startTime = System.currentTimeMillis();
		
		System.out.println("listening...");
		try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byteInputStream = new ByteArrayInputStream(packet.getData());
		inputStream = new ObjectInputStream(byteInputStream);
		try { 
			array = (ArrayList<Integer>)inputStream.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Received integer array.");
		for(int i : array) {
			System.out.println(i);
		}
	}
}
