package server;

import cmdGUI.CMDGUI;
import data.DataBase;
import data.buildings.Building;
import data.buildings.BuildingType;
import data.user.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerClient implements Runnable {

    private Socket socket;
    private AdminServer server;
    private DataBase dataBase;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private String username;
    private String password;
    private Boolean loggedIn = false;
    private CMDGUI cmdgui;

    public ServerClient (Socket socket, AdminServer server, DataBase dataBase) {
        this.socket = socket;
        this.server = server;
        this.dataBase = dataBase;
        this.cmdgui = new CMDGUI();
    }

    public void writeUTF ( String text ) {
        System.out.println("Got message for client");
        try {
            this.dataOutputStream.writeUTF(text);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
            this.objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.dataInputStream = new DataInputStream(this.socket.getInputStream());
            this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());


            //Step 0: Show server details.
            dataOutputStream.writeUTF("House administration Server 0.1");

            //Step 1: Read the given user account. TODO: Errors when user is not known.
            while (!loggedIn) {
                this.username = dataInputStream.readUTF();
                this.password = dataInputStream.readUTF();
                String loginmessage = "User unknown";
                if (this.dataBase.checkUserRegisterd(this.username, this.password)) {
                    loggedIn = true;
                    dataOutputStream.writeBoolean(loggedIn);
                    loginmessage = "#### " + this.username + " has logged in ! #####";
                    System.out.println(loginmessage);
                }
                dataOutputStream.writeUTF(loginmessage);
            }
//            this.server.sendToAllClients("#### " + this.name + " joined the chat!");

            //Step 2: Let the user choose a menu option.
            String menuOption = "";
            while ( !menuOption.equals("quit") ) {
                menuOption = dataInputStream.readUTF(); //Step 2.1
                System.out.println(this.username + " chose option: " + menuOption);

                if (menuOption.equals(cmdgui.getMenuItems().get(0).getMenuName())){ //Option 1. UserAdd
                    User user = (User) objectInputStream.readObject();
                    dataBase.getUsers().add(user);
                    System.out.println("User: " + user.getLoginname() + " was added by: " + this.username);
                }
                if (menuOption.equals(cmdgui.getMenuItems().get(1).getMenuName())){//Option 2. UserDel
                    dataOutputStream.writeUTF("Which user do you want to delete? Tip: first use command 'UserShow'");
                    String user = dataInputStream.readUTF();
                    User deleteUser = null;
                    boolean userFound = false;
                    for (User user1 : dataBase.getUsers()){
                        if (user1.getLoginname().equals(user)){
                            deleteUser = user1;
                            userFound = true;
                        }
                    }
                    if (userFound){
                        dataBase.getUsers().remove(deleteUser);
                        String response = "User: " + user + " Deleted by: " + this.username;
                        dataOutputStream.writeUTF(response);
                        System.out.println(this.username + " deleted " + deleteUser.getLoginname() + " (" + deleteUser + ")");
                    }
                }
                if (menuOption.equals(cmdgui.getMenuItems().get(2).getMenuName())){ //Option 3. UserShow
                    System.out.println("Sending userdata from database to: " + this.username);
                    ArrayList<User> userArrayList = dataBase.getUsers();
                    dataOutputStream.writeUTF(userArrayList.size() + ""); //fantastic idea of JKB
                    for (User user : userArrayList){
                        objectOutputStream.writeObject(user);
                    }
                    System.out.println("send users");
                }
                if (menuOption.equals(cmdgui.getMenuItems().get(3).getMenuName())){ //Option 4. BuildingAdd
                    String buildingType = dataInputStream.readUTF();
                    String zipcode = dataInputStream.readUTF();
                    String address = dataInputStream.readUTF();
                    String city = dataInputStream.readUTF();
                    int price = Integer.parseInt(dataInputStream.readUTF());
                    System.out.println(buildingType + " - " + zipcode + " - " + address + " - " + city);

                    BuildingType type = null;
                    if (buildingType.equals("house")){
                        type = BuildingType.HOUSE;
                    } else if (buildingType.equals("condo")){
                        type = BuildingType.CONDO;
                    } else if (buildingType.equals("apartment")){
                        type = BuildingType.APARTMENT;
                    }
                    dataBase.addNewBuilding(zipcode, address, city, type, price);
                    dataOutputStream.writeUTF(buildingType + " added with address '" + address + "' with value " + price);
                }
                if (menuOption.equals(cmdgui.getMenuItems().get(4).getMenuName())){//Option 5. BuildingDel
                    dataOutputStream.writeUTF("What is the address of the building you want to delete? ");
                    String address = dataInputStream.readUTF();

                    Building deleteBuiling = null;
                    boolean buildingFound = false;
                    for (Building b : dataBase.getBuildings()){
                        if (b.getAdres().equals(address)){
                            deleteBuiling = b;
                            buildingFound = true;
                        }
                    }
                    if (buildingFound){
                        dataBase.getBuildings().remove(deleteBuiling);
                        String response = "User: Building at address " + address + " deleted by " + this.username;
                        dataOutputStream.writeUTF(response);
                    }
                }
                if (menuOption.equals(cmdgui.getMenuItems().get(5).getMenuName())){ //Option 5. BuildingrShow
                    System.out.println("Sending userdata from database to: " + this.username);
                    ArrayList<Building> buildingArrayList = dataBase.getBuildings();
                    dataOutputStream.writeUTF(buildingArrayList.size() + ""); //fantastic idea of JKB
                    for (Building building : buildingArrayList){
                        objectOutputStream.writeObject(building);
                    }
                    System.out.println("send buildings");
                }
                objectOutputStream.flush();
                dataOutputStream.flush();
            }

            this.socket.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
