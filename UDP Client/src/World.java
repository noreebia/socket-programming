import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import model.*;
import processing.core.PApplet;

public class World extends PApplet{
 	InetAddress serverAddress;
 	int serverPort;
	DatagramSocket socket;
	DatagramPacket packet;
			
	int x,y;
	int pos[] = new int[2];
	byte buf[] = new byte[8192];
	
	User user = new User(this);
	Player player = new Player();
	
	Data data = new Data();
	
	int connectionID;
	
	ExecutorService executor = Executors.newCachedThreadPool();
	ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
	
	public World() {
		System.out.println("initializing world");
		try {
			serverAddress = InetAddress.getByName("localhost");
			serverPort = 50000;
			socket = new DatagramSocket();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		packet = new DatagramPacket(buf, buf.length, serverAddress, serverPort);
		
		
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
		
		connectionID = byteArrayToInt(packet.getData());
		player.setID(connectionID);
		System.out.println("My ID: " + connectionID);
		
		executor.execute(new InputHandlingThread(socket, data));
		ses.scheduleWithFixedDelay(new OutputHandlingThread(socket, serverAddress, 50001, player), 0, 8, TimeUnit.MILLISECONDS);
	}
	
	public int byteArrayToInt(byte[] b) {
		ByteBuffer bb = ByteBuffer.wrap(b);
		return bb.getInt();
	}
	
	public void settings() {
		size(1200,800);
	}
	
	public void setup() {
		fill(0);
	}
	
	public void draw() {
		background(255);
		
		user.run();
		player.cloneInfoOf(user);
		displayGameObjectData();
	}
	
	public void displayGameObjectData() {
		for(Player p: data.getPlayers()) {
			if(p.getID()!= connectionID) {
				ellipse(p.getX(), p.getY(), 2 * p.getSize(), 2* p.getSize());
			}
			else {
				ellipse(p.getX(), p.getY(), 5, 5);
			}
		}
	}
	
	public void keyPressed() {
		if (keyCode == UP) {
			user.shouldFace(0, true);
		}

		if (keyCode == LEFT) {
			user.shouldFace(1, true);
		}

		if (keyCode == DOWN) {
			user.shouldFace(2, true);
		}

		if (keyCode == RIGHT) {
			user.shouldFace(3, true);
		}
		if (key == 'w') {
			user.shouldMove(0, true);
		}
		if (key == 'a') {
			user.shouldMove(1, true);
		}
		if (key == 's') {
			user.shouldMove(2, true);
		}
		if (key == 'd') {
			user.shouldMove(3, true);
		}
		if (key == ' ') {
			//user.shoot();
		}
	}

	public void keyReleased() {
		if (keyCode == UP) {
			user.shouldFace(0, false);
		}

		if (keyCode == LEFT) {
			user.shouldFace(1, false);
		}

		if (keyCode == DOWN) {
			user.shouldFace(2, false);
		}

		if (keyCode == RIGHT) {
			user.shouldFace(3, false);
		}
		if (key == 'w') {
			user.shouldMove(0, false);
		}
		if (key == 'a') {
			user.shouldMove(1, false);
		}
		if (key == 's') {
			user.shouldMove(2, false);
		}
		if (key == 'd') {
			user.shouldMove(3, false);
		}
		if (key == ' ') {
			//user.stopShooting();
		}
	}

}
