import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import processing.core.PApplet;

public class World extends PApplet{
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
	
	public World() {
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
		/*
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
		*/
	}
	
	public void settings() {
		size(1200,800);
	}
	
	public void setup() {
		fill(0);
	}
	
	public void draw() {
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
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(pos[0] + ", " + pos[1]);

		background(255);
		ellipse(pos[0],pos[1],20,20);
	}
}
