import javax.swing.*;


public class ClientThread extends Thread{
	DatabaseHandler dbHandler;
	JTextArea textArea;
	
	ClientThread(JTextArea textArea,DatabaseHandler dbHandler){
		this.dbHandler=dbHandler;
		this.textArea=textArea;
	}
	
	public void run(){
		while(true){
			if(textArea.getText() != dbHandler.getChatroomContents()){
				textArea.setText(dbHandler.getChatroomContents());
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
