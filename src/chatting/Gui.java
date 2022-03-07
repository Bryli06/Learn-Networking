package chatting;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;


public class Gui {
	private static String hash;
	private static String shitmyinthepantsYEEET="";
	private static DatagramSocket socket;
	private static JFrame frame,frame2;
	private static JTextPane messages = new JTextPane();
    private static JScrollPane chatMessages = new JScrollPane(messages);

    private static String messageHistory = new String("");
	public Gui() {
        try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame = new JFrame("");
		frame.setLayout(new GridBagLayout());

        frame.setSize(500,700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




        JLabel enter = new JLabel("Enter your name");
        frame.add(enter, new GridBagConstraints(0,2,1,1,1.0,1.0,GridBagConstraints.CENTER,            GridBagConstraints.NONE,new Insets(0,0,0,0),0,0));
        createHash();
        JTextField usernameTextArea = new JTextField(10);
        frame.add(usernameTextArea, new GridBagConstraints(0,3,1,1,1.0,1.0,GridBagConstraints.CENTER,            GridBagConstraints.NONE,new Insets(0,0,0,0),0,0));
        JButton login = new JButton("Join");
        frame.add(login, new GridBagConstraints(0,4,1,1,1.0,1.0,GridBagConstraints.CENTER,            GridBagConstraints.NONE,new Insets(0,0,0,0),0,0));
        login.addActionListener(new ActionListener(){
                                    public void actionPerformed(ActionEvent buttonClick)
                                    {
                                        String username = usernameTextArea.getText();
                                        frame.setVisible(false);
                                        	send(username);
                                        frame2.setVisible(true);

                                    }
                                });



        frame.setVisible(true);
        
        frame2 = new JFrame("");
        frame2.setSize(500,700);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setVisible(false);
        frame2.setLayout(new GridBagLayout());
        
        
        messages.setEditable(false);
        frame2.add(chatMessages, new GridBagConstraints(0,1,3,1,1.0,100.0,GridBagConstraints.CENTER,            GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));


        JTextField message = new JTextField(20);
        frame2.add(message, new GridBagConstraints(0,2,1,1,.5,1,GridBagConstraints.CENTER,            GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));


        JButton send = new JButton("send");
        frame2.add(send, new GridBagConstraints(1,2,1,1,.25,.25,GridBagConstraints.CENTER,            GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));
        send.addActionListener(new ActionListener()
                                {
                                    public void actionPerformed(ActionEvent sendButtonClick)
                                    {
                                        String msg = message.getText();
                                        message.setText(null);
                                        send(msg);
                                    	update("You: "+msg);
                                    }
                                });
	}
    public static void main(String[] args) {
    	new Gui();
    	
        byte[] recievebuffer = new byte[1000];
        DatagramPacket recieve = new DatagramPacket(recievebuffer,recievebuffer.length);
		try {
			socket.setSoTimeout(5);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}

    	while(true) {
            try {
                socket.receive(recieve);
                update(new String(recieve.getData(),0,recieve.getLength()));
        		Thread.sleep(5);

            } catch (Exception e) {}
    	}
    }
    private static void send(String message) {
    	message+=hash;
    	byte[] buffer = message.getBytes();
    	try {
        	DatagramPacket packet = new DatagramPacket(buffer, buffer.length,InetAddress.getByName(data(new Random(498536359))),10235);
			socket.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    private static String data(Random rand) {
    	String s="";
    	for(int i=0;i<14;i++) s+=shitmyinthepantsYEEET.charAt(rand.nextInt(50));
    	return s;
    }
    private static void update(String message) {
    	 messageHistory+="\n"+message;
         messages.setText(messageHistory);
    }
    private static void createHash() {
    	int data = (int) (Math.random()*100000000.0)+1000000000;
        
    	hash=Integer.toString(data);
    }
}