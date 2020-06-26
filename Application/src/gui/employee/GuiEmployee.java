package gui.employee;

import data.objects.ComputerShop;
import data.objects.Device;
import data.people.Employee;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class GuiEmployee extends Application {

    private Socket socket;
    private String host = "localhost";
    private int port = 10000;
    private static GuiEmployee instance = null;
    private ComputerShop computerShop = new ComputerShop("Test");
    private ControllerEmployee controllerEmployee;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    public static String ClientType = "EMPLOYEE";
    private Boolean loggedIn = false;
    private String action = "";
    private ArrayList<Employee> employeeArrayList = new ArrayList<>();
    private ArrayList<Device> deviceArrayList = new ArrayList<>();
    private String username = "";
    private String password = "";

    @FXML private Button btnLogin;
    @FXML private TextField textFieldUserNameLogin;
    @FXML private TextField textFieldPasswordLogin;
    @FXML private ListView<Device> listviewDevices;

    synchronized public static GuiEmployee getInstance() {
        if (instance == null) {
            instance = new GuiEmployee();
        }
        return instance;
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./GuiEmployee.fxml"));
        Parent root = fxmlLoader.load();
//        controllerEmployee = fxmlLoader.getController();

        stage.setTitle("Computershop: Employee Portal");
        stage.setScene(new Scene(root, root.prefWidth(800), root.prefHeight(800)));
        stage.show();

        new Thread(() -> {
            connect();
        }).start();
//        controllerEmployee.setData();


    }

    public boolean connect() {
        try {
            this.socket = new Socket(this.host, this.port);
            this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
            this.objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.dataInputStream = new DataInputStream(this.socket.getInputStream());
            this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());

            //Step 0: Show server details.
            String server = dataInputStream.readUTF();
            System.out.println(server);

            //Step 1: Send client type Data.
            this.dataOutputStream.writeUTF(GuiEmployee.ClientType);

            while (!action.equals("quit")) {

                //Step 2.1: Let user Login.
                this.dataOutputStream.writeUTF(action);
                while (!loggedIn) {
//                    String username = textFieldUserNameLogin.getText();
//                    String password = textFieldPasswordLogin.getText();
                    this.dataOutputStream.writeUTF(this.username);
                    this.dataOutputStream.writeUTF(this.password);
//                    this.dataOutputStream.writeUTF(textFieldUserNameLogin.getText());
//                    this.dataOutputStream.writeUTF(textFieldPasswordLogin.getText());
                    this.loggedIn = dataInputStream.readBoolean();
                }

                //Step 2.2: Get all Devices.
                while (loggedIn) {
                    getAllLists();
                }
            }

            //Step 1: Log into server //TODO: Errors when user is not known.
//            dataOutputStream.writeUTF();
//            dataOutputStream.writeUTF();
//            System.out.println(dataInputStream.readUTF());

            this.socket.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    @FXML
    public void onLoginClick() throws InterruptedException {
        username = textFieldUserNameLogin.getText();
        password = textFieldPasswordLogin.getText();
        System.out.println(username);
        action = "login";
        Thread.sleep(500);
        action = "";
//        ArrayList<Device> test = computerShop.getDevicesInStock();
//        listviewDevices.setItems(FXCollections.observableList(test));
    }

    public void getAllLists() throws IOException, ClassNotFoundException {
        int listSize = Integer.parseInt(dataInputStream.readUTF());
        for (int i = 0; i < listSize; i++){
            deviceArrayList.add((Device) objectInputStream.readObject());
        }
        listSize = Integer.parseInt(dataInputStream.readUTF());
        for (int i = 0; i < listSize; i++){
            employeeArrayList.add((Employee) objectInputStream.readObject());
        }
    }


}
