package server;

import data.objects.ComputerShop;
import data.objects.Device;
import data.people.Customer;
import data.people.Employee;
import gui.customer.GuiCustomer;
import gui.employee.GuiEmployee;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ShopServerClient implements Runnable {

    private Socket socket;
    private ShopServer server;
    private ComputerShop computerShopDatabase;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private String username;
    private String password;
    private Boolean loggedIn = false;

    public ShopServerClient (Socket socket, ShopServer server, ComputerShop dataBase) {
        this.socket = socket;
        this.server = server;
        this.computerShopDatabase = dataBase;
    }

    public void writeUTF ( String text ) {
        System.out.println("Got message for client");
        try {
            this.dataOutputStream.writeUTF(text);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        try {
            this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
            this.objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.dataInputStream = new DataInputStream(this.socket.getInputStream());
            this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());

            //Step 0: Show server details.
            dataOutputStream.writeUTF("ComputerShopServer 0.1");

            //Step 1.1: Get the connected Client Type.
            String type = dataInputStream.readUTF();

            if (type.equals(GuiEmployee.ClientType)){
                String action = "";
                while (!action.equals("quit")){
                    //Step 2.1: Let user login.
                    if (action.equals("login")){
                        while (!loggedIn) {
                            this.username = dataInputStream.readUTF();
                            this.password = dataInputStream.readUTF();
                            if (this.computerShopDatabase.checkUserRegisterd(this.username, this.password)) {
                                loggedIn = true;
                                dataOutputStream.writeBoolean(loggedIn);
                                System.out.println("#### " + this.username + " has logged in ! #####");
                            }
                        }
                    }
                }

                dataOutputStream.writeUTF(computerShopDatabase.getDevicesInStock().size() + "");
                for (Device device: computerShopDatabase.getDevicesInStock()) {
                    objectOutputStream.writeObject(device);
                }
                dataOutputStream.writeUTF(computerShopDatabase.getWorkers().size() + "");
                for (Employee employee : computerShopDatabase.getWorkers()) {
                    objectOutputStream.writeObject(employee);
                }

            }

//            else if (type.equals(GuiCustomer.ClientType)){
//                dataOutputStream.writeUTF(computerShopDatabase.getDevicesInStock().size() + ""); //Send size of arraylist.
//                    for (Device device : computerShopDatabase.getDevicesInStock()){
//                        objectOutputStream.writeObject(device);
//                    }
//
//                String action = "";
//                while (!action.equals("quit")){
//                    //Step 1.2: Send the availableDevices.
//                    action = dataInputStream.readUTF();
//                    System.out.println(action);
//
//                    if (action.equals(GuiCustomer.BuyItem)){
//                        Device device = (Device) objectInputStream.readObject();
//                        Device deviceToRemove = null;
//                        for (Device device1 : computerShopDatabase.getDevicesInStock())
//                            if (device1.equals(device)){
//                                deviceToRemove = device1;
//                        }
//                        computerShopDatabase.getDevicesInStock().remove(deviceToRemove);
//                    }

//                    if (dataInputStream.readUTF().equals(GuiCustomer.)){
//                        receiveAllDevicesInArray(objectInputStream,dataInputStream,computerShopDatabase.getDevicesInStock());
//
//                    }

        this.socket.close();

    } catch (IOException ioException) {
            ioException.printStackTrace();
        }

//        public void receiveAllDevicesInArray(ObjectInputStream objectInputStream, DataInputStream dataInputStream, ArrayList arrayList) throws IOException, ClassNotFoundException {
//
//        int amount = Integer.parseInt(dataInputStream.readUTF());
//
//        for (int i = 0; i < amount; i++){
//            arrayList.clear();
//            arrayList.add(objectInputStream.readObject());
//        }
    }
}
