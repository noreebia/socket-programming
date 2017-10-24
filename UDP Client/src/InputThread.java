import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class InputThread implements Runnable{

	int[] pos, temp;
	DatagramPacket packet;
	DatagramSocket socket;
	ByteArrayInputStream bais;
	ObjectInputStream is;
	byte[] buf;
	
	public InputThread(DatagramSocket socket, int[] pos, ByteArrayInputStream bais, ObjectInputStream is, byte[] buf) {
		System.out.println("Input handler created.");
		this.socket = socket;
		this.pos = pos;
		this.bais = bais;
		this.is = is;
		this.buf = buf;
	}
	
	public void run() {
		
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
			temp = (int[])is.readObject();
			System.arraycopy(temp, 0, pos, 0, temp.length);
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
		System.out.println(pos[0] + ", " + pos[1]);
	}	
}
