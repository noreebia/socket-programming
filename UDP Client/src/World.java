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

import client_exclusive.DisplayHandler;
import client_exclusive.User;
import control.DataController;
import model.*;
import processing.core.PApplet;

public class World extends PApplet{
 	InetAddress serverAddress;
 	int serverPort;
	DatagramSocket socket;
	DatagramPacket packet;
			
	byte buf[] = new byte[8192];
	
	User user = new User(this);
	Player player = new Player(user.getBullets());
		
	DataController dataController = new DataController();
	
	short connectionID;
	
	ExecutorService executor = Executors.newCachedThreadPool();
	ScheduledExecutorService ses = Executors.newScheduledThreadPool(2);
	
	DisplayHandler displayHandler;
	
	public World() {
		
		System.out.println("initializing world");
		try {
			serverAddress = InetAddress.getByName("localhost");
			//serverAddress = InetAddress.getByName("192.168.0.14");
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
		try {
		System.out.println("Sent packet to server");
		
		packet = new DatagramPacket(buf,buf.length);
		try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		connectionID = byteArrayToShort(packet.getData());
		player.setID((short)connectionID);
		System.out.println("My ID: " + connectionID);
		
		executor.execute(new InputHandlingThread(socket, dataController, connectionID, user));
		ses.scheduleAtFixedRate(new OutputHandlingThread(socket, serverAddress, 50001, player), 0, 8, TimeUnit.MILLISECONDS);
		
		displayHandler = new DisplayHandler(this, connectionID, dataController, user);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public short byteArrayToShort(byte[] b) {
		ByteBuffer bb = ByteBuffer.wrap(b);
		return bb.getShort();
	}
	
	public void settings() {
		size(1200,800);
	}
	
	public void setup() {
		strokeWeight(1);
		stroke(0);
	}
	
	public void draw() {
		background(255);
		handleBulletEnemyCollision();

		user.run();
		user.writeInfoInto(player);
		displayHandler.displayGameObjectData();
	}
	
	public void handleBulletEnemyCollision() {
		int i;
		for(Bullet b: user.getBullets()) {
			for(i=0; i< dataController.getEnemies().size(); i++) {
				if(getDistance(b, dataController.getEnemies().get(i)) <= b.getSize() + dataController.getEnemies().get(i).getSize() )  {
					player.addHitEnemies(i);
					b.deactivate();
				}
			}
		}
	}
	
	
	
	public double getDistance(GameObject a, GameObject b) {
		float xDistance = a.getX() - b.getX();
		float yDistance = a.getY() - b.getY();
		
		return Math.sqrt( Math.pow(xDistance, 2) + Math.pow(yDistance, 2) );
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
			user.shoot();
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
			user.stopShooting();
		}
	}
}
