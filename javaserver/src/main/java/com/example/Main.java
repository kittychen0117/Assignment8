package com.example;

import javax.swing.JFrame;


public class Main {
    public static void main(String[] args){
        Server obj = new Server();
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.setSize(300, 100);
        obj.setVisible(true);
    }
}