import java.net.*;
import java.io.*;
import java.util.*;
import java.util.*;

public class Server {

	static DatabaseHandler dbHandler = new DatabaseHandler();
	static ArrayList<ServerThread> threads=new ArrayList();
	static ServerSocket s;

	public static void main(String[] args) {

		try {
			s=new ServerSocket(9090);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server Running...");

		while(true){
			try {
				threads.add(new ServerThread(s.accept()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			threads.get(threads.size()-1).start();
		}
	}

	public static class ServerThread extends Thread
	{
		Socket socket;
		PrintWriter out;
		BufferedReader in;
		BufferedReader stdIn;;

		ServerThread(Socket socket)
		{
			System.out.println("Connection established. Thread number:"+threads.size());
			this.socket=socket;
			try {
				this.out=new PrintWriter(socket.getOutputStream(),true);
				this.in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				this.stdIn=new BufferedReader( new InputStreamReader(System.in));
			} catch (IOException e) {
				e.printStackTrace();
			}interrupt();
			out.println("InitEncode: "+ dbHandler.getChatroomContents());
		}

		public void run()
		{			
			while(true)
			{
				try {
					String chatroomContents;
					String messageFromClient=in.readLine();
					if(messageFromClient != null){
						dbHandler.updateChatroomContents(messageFromClient);
						//System.out.println(messageFromClient);
						chatroomContents=dbHandler.getChatroomContents();
						//System.out.println(chatroomContents);
						sendToAll(messageFromClient);

					}
				} catch (IOException e) {
					//e.printStackTrace();
					System.out.println("Client has disconnected");
					if(threads.remove(this)){
						System.out.println("Thread removed");
						System.out.println(threads.size());
					}
					this.interrupt();
					break;
				}
			}
		}

		public void send() throws IOException{
			out.println("Server");	
		}

		public void send(String message) throws IOException{
			out.println(message);	
		}

		public void sendToAll(){
			for(ServerThread thread:threads){
				try {
					thread.send();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		public void sendToAll(String message){
			for(ServerThread thread:threads){
				try {
					thread.send(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

