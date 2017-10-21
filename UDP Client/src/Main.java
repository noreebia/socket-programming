import java.io.IOException;
import java.net.*;

public class Main {
	static InetAddress serverAddress;
	static DatagramSocket socket;
	public static void main(String[] args) {
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		byte buf[] = new byte[256];
		String message = "Hello";
		buf = message.getBytes();
		try {
			serverAddress = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}		
		DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddress, 50000);
		
		long startTime = System.currentTimeMillis();
		while( System.currentTimeMillis() - startTime < 3000) {
			System.out.println("sending...");
			try {
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
