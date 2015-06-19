import java.net.*;
import java.io.*;
import java.util.*;

public class Client {

    public static void main(String[] args) {
        try {
            int i = 0;
            String cin;
            Socket c = new Socket("127.0.0.1", 9090);
            System.out.println("Connection established.");
            PrintWriter out = new PrintWriter(c.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(c.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            ClientThread thread=new ClientThread(out, stdIn);
            thread.start();
            run(input);
        } catch (IOException e) {
            System.out.println("Server is not running.");
            System.exit(1);
        }
    }

    public static void run(BufferedReader input) {
        try {
            while (true) {
               System.out.println(input.readLine());
            }
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
}

class ClientThread extends Thread{
	
	PrintWriter out;
	BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	ClientThread(PrintWriter out, BufferedReader stdIn){
		this.out=out;
		this.stdIn=stdIn;
	}
	
	public void run(){
		while(true){
			try {
				out.println(stdIn.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}