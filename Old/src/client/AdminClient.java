package client;

import cmdGUI.CMDGUI;
import cmdGUI.MenuItem.MenuItem;
import data.user.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminClient {

    private Socket socket;
    private String host;
    private int port;
    private boolean loggedIn = false;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private CMDGUI cmdgui;

//    private BorderPane mainLayout;
//    private BorderPane menuLayout;
//    @FXML  TextField TextFieldUserName = new TextField();
//    @FXML  TextField TextFieldPassword = new TextField();
//    @FXML  TextField TextFieldCommand = new TextField();
//    @FXML  TextArea TextAreaOutput = new TextArea();
//    @FXML  Label LabelCommands = new Label("Test");
//    @FXML  Button ButtonSubmitCommand = new Button();
//    @FXML  Button ButtonSubmitLogin = new Button();

    public AdminClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.cmdgui = new CMDGUI();
    }

    public boolean connect () {
        try {
            this.host = "localhost";
            this.port = 10000;
            this.cmdgui = new CMDGUI();
            this.socket = new Socket(this.host, this.port);

            this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
            this.objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.dataInputStream = new DataInputStream(this.socket.getInputStream());
            this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());


            Scanner scanner = new Scanner( System.in );

            //Step 0: Show server details.
            String server = dataInputStream.readUTF();
            System.out.println(server);

            //Step 1: Log into server //TODO: Errors when user is not known.
            while (!loggedIn) {
                System.out.print("What is your username: ");
//                label.setText("What is your username: ");
                dataOutputStream.writeUTF(scanner.nextLine());
                System.out.print("What is your password: ");
                dataOutputStream.writeUTF(scanner.nextLine());
                loggedIn = dataInputStream.readBoolean();
                System.out.println(dataInputStream.readUTF());
            }

//            new Thread ( () -> {
//                while ( true ) {
//                    try {
//                        System.out.println(dataInputStream.readUTF());
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();

            //Step 2: Let the user choose a option.
            String menuOption = "";
            while ( !menuOption.equals("quit" )) {
                System.out.println();
                    for (MenuItem menuItem : cmdgui.getMenuItems()) {
                        System.out.println(menuItem.getMenuName() + " - " + menuItem.getDescription());
                    }
                    System.out.println("Choose a menu option: ");
                    menuOption = scanner.nextLine();

                if (menuOption.equals(cmdgui.getMenuItems().get(0).getMenuName())){ //Option 1. UserAdd
                    dataOutputStream.writeUTF(menuOption); //Step 2.1
                    System.out.println("Who do you want to add?");
                    String name = scanner.nextLine();
                    System.out.println("Enter a passcode");
                    String passcode = scanner.nextLine();
                    objectOutputStream.writeObject(new User(name,passcode));
                    objectOutputStream.flush();
                    dataOutputStream.flush();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if (menuOption.equals(cmdgui.getMenuItems().get(1).getMenuName())){ //Option 2. UserDel
                    dataOutputStream.writeUTF(menuOption); //Step 2.1
                    System.out.println(dataInputStream.readUTF());
                    String user = scanner.nextLine();
                    dataOutputStream.writeUTF(user);
                    System.out.println(dataInputStream.readUTF());
                    objectOutputStream.flush();
                    dataOutputStream.flush();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if (menuOption.equals(cmdgui.getMenuItems().get(2).getMenuName())){ //Option 3. UserShow
                    dataOutputStream.writeUTF(menuOption); //Step 2.1
                    System.out.println("Waiting for the users...");
                    ArrayList<User> userArrayList = new ArrayList<>();
                    int amountOfUsers = Integer.parseInt(dataInputStream.readUTF());
                    for (int i = 0; i < amountOfUsers; i++){
                        userArrayList.add((User)objectInputStream.readObject());
                    }

                    System.out.println("All the user accounts: ");
                    for (User user : userArrayList){
                        System.out.println(user.getLoginname());
                    }
                    objectOutputStream.flush();
                    dataOutputStream.flush();
                }
                else if (menuOption.equals(cmdgui.getMenuItems().get(3).getMenuName())){ //Option 4. BuildingAdd
                    dataOutputStream.writeUTF(menuOption); //Step 2.1
                    System.out.print("What buidlingtype do you want to add? house / condo / apartment ");
                    String buildingType = scanner.nextLine();
                    dataOutputStream.writeUTF(buildingType);
                    System.out.print("What is the zipcode of the  " + buildingType + "? ");
                    String zipcode = scanner.nextLine();
                    dataOutputStream.writeUTF(zipcode);
                    System.out.print("What is the address of the  " + buildingType + "? ");
                    String address = scanner.nextLine();
                    dataOutputStream.writeUTF(address);
                    System.out.print("What is the city of the  " + buildingType + "? ");
                    String city = scanner.nextLine();
                    dataOutputStream.writeUTF(city);
                    System.out.print("What is the value of the " + buildingType + "? " );
                    String value = scanner.nextLine();
                    dataOutputStream.writeUTF(value);
                    System.out.println(dataInputStream.readUTF());

                    objectOutputStream.flush();
                    dataOutputStream.flush();
                }

                else if (menuOption.equals(cmdgui.getMenuItems().get(4).getMenuName())){ //Option 5. BuildingDel
                    dataOutputStream.writeUTF(menuOption); //Step 2.1
                    System.out.println(dataInputStream.readUTF());
                    String address = scanner.nextLine();
                    dataOutputStream.writeUTF(address);

                    System.out.println(dataInputStream.readUTF());

                    objectOutputStream.flush();
                    dataOutputStream.flush();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                else if (menuOption.equals(cmdgui.getMenuItems().get(5).getMenuName())){ //Option 5. BuilldingShow
                    dataOutputStream.writeUTF(menuOption); //Step 2.1
                    System.out.println("Waiting for the buildings...");
                    ArrayList<Building> buildingArrayList = new ArrayList<>();
                    int amountOfBuildings = Integer.parseInt(dataInputStream.readUTF());
                    for (int i = 0; i < amountOfBuildings; i++){
                        buildingArrayList.add((Building) objectInputStream.readObject());
                    }

                    System.out.println("All the buildings: ");
                    for (Building building : buildingArrayList){
                        System.out.println(building.toString());
                    }
                    objectOutputStream.flush();
                    dataOutputStream.flush();
                } else {
                    System.out.println("Invalid input!");
                }
            }

            this.socket.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not connect with the server on " + this.host + " with port " + this.port + ": " + e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return true;
    }

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        this.cmdgui = new CMDGUI();
//
//        FXMLLoader loader = new FXMLLoader();
////        loader.setLocation(AdminClient.class.getResource("GUI-Login.fxml"));
//        Parent root = FXMLLoader.load(AdminClient.class.getResource("GUI-Menu.fxml"));
//        Scene scene = new Scene(root);
//        StringBuilder menuItems = new StringBuilder();
//        for (MenuItem menuItem : cmdgui.getMenuItems()){
//            menuItems.append(menuItem.getMenuName() + "\n");
//        }
//
//        ButtonSubmitCommand.setOnAction(TextAreaOutput.setText(TextFieldCommand.getText()));
//        this.LabelCommands.setText("test");
//        this.LabelCommands.getText();
//
//
////        this.label = new Label("Test");
////        this.textField = new TextField("");
////        this.submit = new Button("Submit");
////        borderPane.setTop(this.label);
////        borderPane.setCenter(this.textField);
////        borderPane.setBottom(this.submit);
//
////        connect();
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Admininistration Application by Dave and Jan Kees");
//        primaryStage.show();
//    }
}
