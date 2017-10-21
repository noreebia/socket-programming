import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class AlternativeWorld {
 	InetAddress serverAddress;
	DatagramSocket socket;
	DatagramPacket packet;
	
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	ObjectOutputStream os;
	
	ByteArrayInputStream bais;
	ObjectInputStream is;
	
	int x,y;
	int pos[] = new int[2];
	byte buf[] = new byte[8192];
	
	public AlternativeWorld() {
		System.out.println("initializing world");
		pos[0] = 600;
		pos[1] = 400;
		try {
			serverAddress = InetAddress.getByName("localhost");
			socket = new DatagramSocket();
			os = new ObjectOutputStream(baos);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		packet = new DatagramPacket(buf, buf.length, serverAddress, 50000);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Sent packet to server");
		
		packet = new DatagramPacket(buf,buf.length);
		try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void run() {
		while(true) {
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
				pos = (int[])is.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(pos[0] + ", " + pos[1]);
			
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

