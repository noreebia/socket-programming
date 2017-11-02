import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

import control.DataController;
import control.EnemySystem;
import model.*;

public class InputHandlingThread implements Runnable{

	DatagramSocket ioSocket;
	DatagramPacket packet;
	byte[] buf = new byte[8192];
	Player temp;
	DataController dataController;
	
	ByteArrayInputStream bais;
	ObjectInputStream is;
	
	EnemySystem enemySystem;

	public InputHandlingThread(DatagramSocket ioSocket, DataController dataController, EnemySystem enemySystem) {
		System.out.println("Input handler created.");
		this.ioSocket = ioSocket;
		this.dataController = dataController;
		this.enemySystem = enemySystem;
	}
	
	public void run() {
		while(true) {
			System.out.println("Receiving");
			packet = new DatagramPacket(buf,buf.length);
			try {
				ioSocket.receive(packet);
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
			System.out.println("attempting to update data");
			System.out.println("current num of players: " + dataController.getPlayers().size());
			try {
				temp = (Player)is.readObject();
				dataController.updatePlayer(temp);
				System.out.println("received player object from client and data updated");
				System.out.println("number of player bullets: " + temp.getBullets().size());
				
				for(Integer i: temp.getHitEnemies()) {
					//enemySystem.respawnEnemy(i);
					enemySystem.getOriginals().get(i).getHit();
					enemySystem.changeShadowColor(i);
				}
				
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
		}
	}

}
