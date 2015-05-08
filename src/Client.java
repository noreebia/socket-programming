/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
            int i = 0;
            String cin;
            Socket c = new Socket("127.0.0.1", 9090);
            System.out.println("Connection established.");
            PrintWriter out = new PrintWriter(c.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(c.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            ccw = new ClientChatWindow(out, input);
            run(input);
        } catch (IOException e) {
            System.out.println("Server is not running.");
        }
    }

    public static void run(BufferedReader input) {
        try {
            while (true) {
                ccw.jTextArea1.append(input.readLine() + "\n");
            }
        } catch (IOException e) {
            ccw.jTextArea1.append("Server has disconnected." + "\n");
        }
    }
}
