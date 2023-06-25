package com.example.borderpane;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatWithServer extends Thread {
    private int clientNbre;
    private List<Communication> clientConnectes = new ArrayList<>();

    public static void main(String[] args) {
        new ChatWithServer().start();
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(1234);
            System.out.println("Le serveur va démarrer...");
            while (true) {
                Socket s = ss.accept();
                ++clientNbre;
                Communication newCommunication = new Communication(s, clientNbre);
                clientConnectes.add(newCommunication);
                newCommunication.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class Communication extends Thread {
        private Socket socket;
        private int clientNumber;

        Communication(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
        }

        @Override
        public void run() {
            try {
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                OutputStream os = socket.getOutputStream();
                String ip = socket.getRemoteSocketAddress().toString();
                System.out.println("Le client numéro " + clientNumber + " et son IP " + ip);
                PrintWriter pw = new PrintWriter(os, true);
                pw.println("Vous êtes le client " + clientNumber);
                pw.println("Envoyez le message que vous voulez (" + clientNumber + ")");
                while (true) {
                    String userRequest = br.readLine();
                    if (userRequest.contains("=>")) {
                        String[] userMessage = userRequest.split("=>");
                        if (userMessage.length == 2) {
                            String msg = userMessage[1];
                            int numeroClient = Integer.parseInt(userMessage[0]);
                            send(msg, socket, numeroClient);
                        }
                    } else {
                        send(userRequest, socket, -1);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void send(String userRequest, Socket socket, int nbre) throws IOException {
            for (Communication client : clientConnectes) {
                if (client.socket != socket) {
                    if (client.clientNumber == nbre || nbre == -1) {
                        PrintWriter pw = new PrintWriter(client.socket.getOutputStream(), true);
                        pw.println(userRequest);
                    }
                }
            }
        }
    }
}
