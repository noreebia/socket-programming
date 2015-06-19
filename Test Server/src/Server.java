import java.net.*;
import java.io.*;
import java.util.*;
import java.util.*;

public class Server {

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
			System.out.println("Connection established");
			this.socket=socket;
			try {
				this.out=new PrintWriter(socket.getOutputStream(),true);
				this.in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				this.stdIn=new BufferedReader( new InputStreamReader(System.in));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run()
		{			
			while(true)
			{
				try {
					String messageFromClient=in.readLine();
					if(messageFromClient != null){
						sendToAll();
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

		public void sendToAll(){
			for(ServerThread thread:threads){
				try {
					thread.send();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

