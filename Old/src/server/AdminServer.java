//package server;
//
//import data.DataBase;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.ArrayList;
//
//public class AdminServer {
//
//    private int port;
//    private ServerSocket server;
//    private Thread serverThread;
//    private ArrayList<ServerClient> clients;
//    private ArrayList<Thread> threads;
//    private DataBase dataBase;
//
//    public AdminServer ( int port ) {
//        this.port = port;
//        this.clients = new ArrayList<>();
//        this.threads = new ArrayList<>();
//    }
//
//    public boolean start ( ) {
//        try {
//            this.server = new ServerSocket(port);
//            this.dataBase = new DataBase();
//
//            this.serverThread = new Thread( () -> {
//                while ( true ) {
//                    System.out.println("Waiting for clients to connect...");
//                    try {
//                        Socket socket = this.server.accept();
//                        System.out.println("Administration_server connected from " + socket.getInetAddress().getHostAddress() + ".");
//
//                        ServerClient client = new ServerClient(socket, this, this.dataBase);
//                        Thread threadClient = new Thread(client);
//                        threadClient.start();
//                        this.clients.add(client);
//                        this.threads.add(threadClient);
//
//                        System.out.println("Total clients connected: " + this.clients.size());
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        Thread.sleep(100);
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Thread.yield();
//                }
//
//            });
//
//            this.serverThread.start();
//            System.out.println("Administartion_server is started and listening on port " + this.port);
//
//        } catch (IOException e) {
//            System.out.println("Could not connect: " + e.getMessage());
//            return false;
//        }
//
//        return true;
//    }
//
//    public void sendToAllClients ( String text ) {
//        for ( ServerClient client : this.clients ) {
//            client.writeUTF(text);
//        }
//    }
//
//}
