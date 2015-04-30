package clientgui;

/**
 *
 * @author Soo-Young
 */
import java.net.*;
import java.io.*;
import java.util.*;

public class Client {

        static ClientChatWindow ccw;
	public static void main(String[] args) {
		try {
			int i=0;
			String cin;
			Socket c = new Socket("127.0.0.1",9090);
			System.out.println("Connection established.");
			PrintWriter out = new PrintWriter(c.getOutputStream(),true);
			BufferedReader input=new BufferedReader(new InputStreamReader(c.getInputStream()));
			BufferedReader stdIn =new BufferedReader( new InputStreamReader(System.in));
                        ccw=new ClientChatWindow(out, input);
			ClientThread ct=new ClientThread(out,input,stdIn,ccw);
			ct.start();
                        ccw.run();
		} catch (IOException  e) {
			System.out.println("Server is not running.");
		} 
	}
}

