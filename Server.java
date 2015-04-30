import java.net.*;
import java.io.*;
import java.util.*;
import java.util.*;

public class Server {

	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		try {
			String input;
			ServerSocket s=new ServerSocket(9090);
			System.out.println("Server Running...");
			Socket c=s.accept();
			System.out.print("Connection established.\n");
			PrintWriter out = new PrintWriter(c.getOutputStream(),true);
			BufferedReader in=new BufferedReader(new InputStreamReader(c.getInputStream()));
			BufferedReader stdIn =new BufferedReader( new InputStreamReader(System.in));
			ServerThread st=new ServerThread(out,in,stdIn);
			st.start();
			while(true)
			{
				System.out.println(in.readLine());
			}
		} catch (IOException e) {
			System.out.println("Client has disconnected.");
		}
	}
}

class ServerThread extends Thread
{
	PrintWriter out;
	BufferedReader in;
	BufferedReader stdIn;;
	ServerThread(PrintWriter pw, BufferedReader br, BufferedReader br2)
	{
		this.out=pw;
		this.in=br;
		this.stdIn=br2;
	}
	public void run()
	{
		try {
			while(true)
			{
				out.println("Server:"+stdIn.readLine() );	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
