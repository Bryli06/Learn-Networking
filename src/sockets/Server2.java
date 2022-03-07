package sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server2 {
	private final static int port = 10256;
	private static ArrayList<instance2> each = new ArrayList<instance2>();
	private static ServerSocket server;
	public static void sendMessage(String s,instance2 from) {
		for(int i=0;i<each.size();i++){
			instance2 current=each.get(i);
			if(current==from) continue;
			current.getOut().println("from "+from.getname()+": "+s);
			current.getOut().flush();
		}
	}
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
					String name = in.readLine();
					instance2 t = new instance2(socket,in,out,name);
					t.start();
					each.add(t);
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
class instance2 extends Thread{
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private String name;
	instance2(Socket socket, BufferedReader in, PrintWriter out,String name){
		this.socket=socket;
		this.in=in;
		this.out=out;
		this.name=name;
	}
	public PrintWriter getOut() {return this.out;}
	public String getname() {return this.name;}
	public void run() {
		while(true) {
		try {
			String input = in.readLine();
			Server2.sendMessage(input,this);
			Thread.sleep(5);
		} catch(IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}