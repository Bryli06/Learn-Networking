package chatting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {
	private final static int port = 10235;
	private static ArrayList<instance2WithPackets> each = new ArrayList<instance2WithPackets>();
	private static DatagramSocket socket;
	private static byte[] buffer;
	public static void sendMessage(instance2WithPackets packet) {
		boolean exists = false;
		String name=null;
		for(int i=0; i<each.size();i++) {
			if(each.get(i).equals(packet)) {
				exists=true; 
				name=each.get(i).getName();
				packet.setName(name);
				each.set(i, packet);
			}
		}
		if(exists) {
			String returnS="From "+name+": "+packet.getitem();
			System.out.println(returnS);
			buffer=returnS.getBytes();
			for(int i=0; i<each.size();i++) {
				if(!each.get(i).equals(packet)) {
					try {
						socket.send(new DatagramPacket(buffer, buffer.length,each.get(i).getAddress(),each.get(i).getPort()));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		else {
			System.out.println("New person: " +packet.getName()+", ip"+packet.getAddress()+":"+packet.getPort());
			each.add(packet);
		}
	}
	private static void sendResponse(DatagramSocket socket, InetAddress address, int clientPort, byte[] response) { 
		try { 
			DatagramPacket sendPacket = new DatagramPacket(response, response.length, address, clientPort);
			socket.send(sendPacket);
			
		} catch (IOException e) {
			
		}     
	}
	public static void main(String[] args) {
		try {
			socket = new DatagramSocket(port);
			System.out.println("online");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(socket!=null) {
			while(true){
				DatagramPacket receivePacket; 
				buffer = new byte[1000];
				receivePacket= new DatagramPacket(buffer,buffer.length);
				try {
					socket.receive(receivePacket);
					String message = new String(receivePacket.getData(),0,receivePacket.getLength());
					instance2WithPackets send = new instance2WithPackets(receivePacket.getAddress(), receivePacket.getPort(), message.substring(0, message.length()-10),Integer.parseInt(message.substring(message.length()-10, message.length())));
					sendMessage(send);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
class instance2WithPackets {
	private int hash;
	private String item;
	private InetAddress address;
	private int clientPort;
	private String name;
	instance2WithPackets(InetAddress address, int clientPort, String item,int hash){
		this.address=address;
		this.clientPort=clientPort;
		this.item=item;
		this.hash=hash;
		this.name=this.item;
	}
	public boolean equals(Object obj) {
		instance2WithPackets s=(instance2WithPackets) obj;
		return (this.hash==s.getHash());
	}
	public void setName(String s) {
		this.name=s;
	}
	public String getName() {
		return name;
	}
	public InetAddress getAddress() {
		return address;
	}
	public int getPort() {
		return clientPort;
	}
	public int getHash() {return hash;}
	public String getitem() {return this.item;}
}