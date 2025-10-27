package com.sockets;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader buffIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter buffOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             Scanner scan = new Scanner(System.in)) {

            System.out.println("Connected to server at " + HOST + ":" + PORT);

            while (true) {
                System.out.print("You: ");
                String msgToSend = scan.nextLine();
                if (msgToSend == null || msgToSend.trim().isEmpty()) {
                    System.out.println("Invalid input. Please enter a message.");
                    continue;
                }

                buffOut.write(msgToSend);
                buffOut.newLine();
                buffOut.flush();

                String response = buffIn.readLine();
                if (response == null) {
                    System.out.println("Server closed the connection.");
                    break;
                }
                System.out.println("Server: " + response);

                if (msgToSend.equalsIgnoreCase("BYE")) {
                    break;
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + HOST);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("I/O error occurred.");
            e.printStackTrace();
        }
        System.out.println("Connection closed.");
    }
}
