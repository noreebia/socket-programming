import java.io.*;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.net.*;

public class Main {
	static DatagramSocket socket;
	public static void main(String[] args) throws IOException {
		try {
			socket = new DatagramSocket(50000);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		byte[] buf = new byte[8192];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		
		ByteArrayInputStream byteInputStream;
		ObjectInputStream inputStream;
				
		System.out.println("listening...");
		try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		byteInputStream = new ByteArrayInputStream(packet.getData());
		inputStream = new ObjectInputStream(byteInputStream);
		
		Data data = null;
		try { 
			data = (Data)inputStream.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		data.displayContents();
		System.out.println("received");
	}
}
