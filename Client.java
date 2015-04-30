import java.net.*;
import java.io.*;
import java.util.*;

public class Client {

	public static void main(String[] args) {
		try {
			int i=0;
			String cin;
			Socket c = new Socket("127.0.0.1",9090);
			System.out.println("Connection established.");
			PrintWriter out = new PrintWriter(c.getOutputStream(),true);
			BufferedReader input=new BufferedReader(new InputStreamReader(c.getInputStream()));
			BufferedReader stdIn =new BufferedReader( new InputStreamReader(System.in));
			ClientThread ct=new ClientThread(out,input,stdIn);
			ct.start();
			while(true)
			{
				out.println("Client:"+stdIn.readLine());
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Server is not running.");
		}
	}
}

class ClientThread extends Thread
{
	PrintWriter out;
	BufferedReader input;
	BufferedReader stdIn;

	ClientThread(PrintWriter pw, BufferedReader br, BufferedReader br2)
	{
		this.out=pw;
		this.input=br;
		this.stdIn=br2;
	}

	public void run()
	{
		try {
			while(true)
			{
				System.out.println(input.readLine());
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}