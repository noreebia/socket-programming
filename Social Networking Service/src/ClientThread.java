import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.*;

public class ClientThread extends Thread{

	JTextArea textArea;
	BufferedReader input;

	ClientThread(JTextArea textArea,BufferedReader input){
		this.textArea=textArea;
		this.input=input;
	}

	public void run(){
		while(true){
			try {
				String processedMessage;
				String messageFromServer = input.readLine();
				if(messageFromServer != null){

					if(messageFromServer.startsWith("InitEncode:")){
						textArea.setText(messageFromServer.substring(12)+"\n");
					}
					else{
						textArea.append(messageFromServer + "\n");
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}
