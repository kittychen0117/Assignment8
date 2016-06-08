package com.example;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import java.awt.Color;
import java.awt.FlowLayout;

/**
 * Created by Gary on 16/5/28.
 */
public class Server extends JFrame implements Runnable{
    private Thread thread;
    private ServerSocket servSock;
    private JLabel label;
    private JProgressBar progressBar;

    public Server(){


        try {
            // Detect server ip
            InetAddress IP = InetAddress.getLocalHost();
            System.out.println("IP of my system is := "+IP.getHostAddress());
            System.out.println("Waiting to connect......");

            // Create server socket
            servSock = new ServerSocket(2000);

            // Create progressBar
            progressBar = new JProgressBar();
            progressBar.setOrientation(JProgressBar.HORIZONTAL);
            progressBar.setMinimum(0);
            progressBar.setMaximum(100);
            progressBar.setValue(0);
            progressBar.setStringPainted(true);
            progressBar.setBorderPainted(true);
            progressBar.setBackground(Color.gray);

            // Create Server Frame
            label = new JLabel();


            this.add(label);
            this.add(progressBar);
            this.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));


            // Create socket thread
            thread = new Thread(this);
            thread.start();
        } catch (java.io.IOException e) {
            System.out.println("Socket啟動有問題 !");
            System.out.println("IOException :" + e.toString());
        } finally{

        }
    }

    @Override
    public void run(){
        // Running for waitting multiple client
        while(true){
            try{
                // After client connected, create client socket connect with client
                Socket clntSock = servSock.accept();
                InputStream in = clntSock.getInputStream();

                System.out.println("Connected!!");

                // Transfer data
                byte[] b = new byte[1024];
                int length;

                // Receive the messages from client
                length = in.read(b);
                String s = new String(b);
                // Parse the answer to the Server Frame
                label.setText(s);
                // Parse the answer to progressBar
                int i, j;
                for (i=0; i<s.length(); i++) {
                    if (s.charAt(i) == '=') break;
                }
                for (j=i; j<s.length(); j++) {
                    if (s.charAt(j) == '.') break;
                }
                int ans = Integer.parseInt(s.substring(i+2, j));
                progressBar.setValue(ans);

                System.out.println("[Server Said]" + s);

            }
            catch(Exception e){
                System.out.println("Error: "+e.getMessage());
            }
        }
    }
}
