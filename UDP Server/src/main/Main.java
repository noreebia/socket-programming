package main;
import java.io.*;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.net.*;

public class Main {
	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.run();
	}
}