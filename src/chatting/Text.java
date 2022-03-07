
package chatting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.Scanner;


public class Text {
	private static String hash;
	private static DatagramSocket socket;
    public static void main(String[] args) {
        try {
        	socket = new DatagramSocket();
            BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
            createHash();
            System.out.println("What is your name?");
            byte[] recievebuffer = new byte[1000];
            DatagramPacket recieve = new DatagramPacket(recievebuffer,recievebuffer.length);
            send(scanner.readLine());
            while(true) {
                if(scanner.ready()) {
                	send(scanner.readLine());
                }
                socket.setSoTimeout(5);
                try {
                	socket.receive(recieve);
                    System.out.println(new String(recieve.getData(),0,recieve.getLength()));
                } catch (Exception e) {}
                Thread.sleep(5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    private static void send(String message) {
    	message+=hash;
    	byte[] buffer = message.getBytes();
    	try {
        	DatagramPacket packet = new DatagramPacket(buffer, buffer.length,InetAddress.getByName("69.181.215.108"),10235);
			socket.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    private static void createHash() {
    	int data = (int) (Math.random()*100000000.0)+1000000000;
        
    	hash=Integer.toString(data);
    }
}
	