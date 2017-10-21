import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.*;

public class Main {
	static DatagramSocket socket;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			socket = new DatagramSocket(50000);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		byte[] buf = new byte[256];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		
		int count = 0;
		String messageFromClient;
		long startTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - startTime < 1000) {
			count++;
			System.out.println("listening...");
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			InetAddress clientIP = packet.getAddress();
			int clientPort = packet.getPort();
			
			
			messageFromClient = new String(packet.getData(), 0, packet.getLength());
			
			System.out.println("message: " + messageFromClient);
			System.out.println("client address: " + clientIP.getHostAddress());
			System.out.println("client port: " + clientPort);
			System.out.println("count: "+ count);
		}
	}
}
