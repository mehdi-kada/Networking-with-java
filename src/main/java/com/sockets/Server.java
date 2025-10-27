package com.sockets;

import java.io.*;
import java.net.*;

public class Server{
    private static final int PORT = 8080;
    public static void main(String[] args) throws Exception{
        try{
            ServerSocket socket = new ServerSocket(PORT);
            Socket clientSocket = socket.accept();
            BufferedReader buffIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter buffOut = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            while (true) {
                String msgFromClient = buffIn.readLine();
                if (msgFromClient == null) {
                    System.out.println("Client disconnected.");
                    break;
                }
                System.out.println("Client: " + msgFromClient);
                buffOut.write("msg received");
                buffOut.newLine();
                buffOut.flush();

                if (msgFromClient.equalsIgnoreCase("BYE")) {
                    break;
                }
            }
            
            clientSocket.close();
            buffIn.close();
            buffOut.close();
            socket.close();
        }catch (IOException e) {
            System.err.println("I/O error occurred.");
            e.printStackTrace();
        }
        System.out.println("Server closed.");
    }
}