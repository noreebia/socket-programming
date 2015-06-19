import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.*;

public class ClientThread extends Thread{
	DatabaseHandler dbHandler;
	JTextArea textArea;
	BufferedReader input;
	
	ClientThread(JTextArea textArea,DatabaseHandler dbHandler,BufferedReader input){
		this.dbHandler=dbHandler;
		this.textArea=textArea;
		this.input=input;
	}
	
	public void run(){
		while(true){
			try {
				String messageFromServer = input.readLine();
				if(messageFromServer != null){
					textArea.setText(dbHandler.getChatroomContents());
				}
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
			
		}
	}
}
