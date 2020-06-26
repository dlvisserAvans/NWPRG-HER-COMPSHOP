package server;

import data.objects.ComputerShop;
import data.people.Employee;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ShopServer {

    private int port;
    private ServerSocket server;
    private Thread serverThread;
    private ArrayList<ShopServerClient> clients;
    private ArrayList<Thread> threads;
    private ComputerShop computerShopDataBase;
    private Boolean running = true;

    public ShopServer ( int port ) {
        this.port = port;
        this.clients = new ArrayList<>();
        this.threads = new ArrayList<>();
    }

    public boolean start () {
        try {
            this.running = true;
            this.server = new ServerSocket(port);
            this.computerShopDataBase = new ComputerShop("");
            for (Employee employee : this.computerShopDataBase.getWorkers()){
                System.out.println(employee.getUserName() + " ----- " + employee.getPassword());
            }

            this.serverThread = new Thread( () -> {
                while ( running ) {
                    System.out.println("Waiting for clients to connect...");
                    try {
                        Socket socket = this.server.accept();
                        System.out.println("ComputerShop_server connected from " + socket.getInetAddress().getHostAddress() + ".");

                        ShopServerClient client = new ShopServerClient(socket, this, this.computerShopDataBase);
                        Thread threadClient = new Thread(client);
                        threadClient.start();
                        this.clients.add(client);
                        this.threads.add(threadClient);

                        System.out.println("Total clients connected: " + this.clients.size());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(100);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Thread.yield();
                }
            });

            this.serverThread.start();
            System.out.println("ComputerShop_Server is started and listening on port " + this.port);

        } catch (IOException e) {
            System.out.println("Could not connect: " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean stop(){
        try {
            this.server.close();
            this.running = false;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return false;
        }
        return true;
    }

    public void sendToAllClients ( String text ) {
        for ( ShopServerClient client : this.clients ) {
            client.writeUTF(text);
        }
