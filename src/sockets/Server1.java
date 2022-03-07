package sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server1 {
	private final static int port = 10256;
	private static ArrayList<Thread> each = new ArrayList<Thread>();
	private static ServerSocket server;
	public static void main(String[] args) {
		try {
			server = new ServerSocket(port);
			System.out.println("online");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(server!=null) {
			while(true){
				Socket socket = null;
				try {
					socket=server.accept();
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter out = new PrintWriter(socket.getOutputStream());
					Thread t = new instance(socket,in,out);
					t.start();
					each.add(t);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}

}
class instance extends Thread{
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	instance(Socket socket, BufferedReader in, PrintWriter out){
		this.socket=socket;
		this.in=in;
		this.out=out;
	}
	public void run() {
		try {
			String input = in.readLine();
			System.out.println(input);
			out.println("you are gay");
			out.flush();
			socket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
}