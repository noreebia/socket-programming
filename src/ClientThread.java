
package clientgui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Soo-Young
 */
class ClientThread extends Thread
{
	PrintWriter out;
	BufferedReader input;
	BufferedReader stdIn;
        ClientChatWindow ccw;
        String string;
	ClientThread(PrintWriter pw, BufferedReader br, BufferedReader br2,ClientChatWindow ccw)
	{
		this.out=pw;
		this.input=br;
		this.stdIn=br2;
                this.ccw= ccw;
	}

	public void run()
	{
		try {
			while(true)
			{
                                ccw.jTextArea1.append(input.readLine()+"\n");
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}