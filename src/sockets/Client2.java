package sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("192.168.1.82", 10256);
            BufferedReader in = new BufferedReader(new InputStreamReader( socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("What is your name?");
            String name = scanner.readLine();
            out.println(name);
            out.flush();
            while(true) {
                if(scanner.ready()) {
                    out.println(scanner.readLine());
                    out.flush();
                }
                if(in.ready()) {
                    System.out.println(in.readLine());
                }
                Thread.sleep(5);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } 
    }

}
