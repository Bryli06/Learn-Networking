package sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {

	public static void main(String[] args) {
		while(true) {
			try {
				Socket socket = new Socket("192.168.1.82", 10256);
				BufferedReader in = new BufferedReader(new InputStreamReader( socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream());
				Scanner scanner = new Scanner(System.in);
				out.println(scanner.nextLine());
				out.flush();
				System.out.println(in.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}

}
