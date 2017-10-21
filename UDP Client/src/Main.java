import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;

public class Main {
	static InetAddress serverAddress;
	static DatagramSocket socket;
	
	public static void main(String[] args) throws IOException {
		Data data = new Data();
		data.displayContents();
		
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		byte buf[] = new byte[8192];
		
		try {
			serverAddress = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}		
		
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream outputStream = new ObjectOutputStream(byteOutputStream);
		outputStream.writeObject(data);
		buf = byteOutputStream.toByteArray();
		
		DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddress, 50000);
		
		socket.send(packet);
		System.out.println("sent");
	}
}
