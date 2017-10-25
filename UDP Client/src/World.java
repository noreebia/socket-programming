import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import processing.core.PApplet;

public class World extends PApplet{
 	InetAddress serverAddress;
	DatagramSocket socket;
	DatagramPacket packet;
			
	int x,y;
	int pos[] = new int[2];
	byte buf[] = new byte[8192];
	
	ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
	
	public World() {
		System.out.println("initializing world");
		pos[0] = 600;
		pos[1] = 400;
		try {
			serverAddress = InetAddress.getByName("localhost");
			socket = new DatagramSocket();
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
		
		InputThread thread = new InputThread(socket, pos);
		ses.scheduleWithFixedDelay(thread, 0, 16, TimeUnit.MILLISECONDS);
	}
	
	public void settings() {
		size(1200,800);
	}
	
	public void setup() {
		fill(0);
	}
	
	public void draw() {
		background(255);
		System.out.println(pos[0] + ", " + pos[1]);
		ellipse(pos[0],pos[1],20,20);
		
		if(millis() >= 10000) {
			ses.shutdown();
			while(!ses.isShutdown()) {
				
			}
			exit();
		}
	}
}
