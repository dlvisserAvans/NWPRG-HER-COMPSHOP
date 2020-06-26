package gui.customer;

import data.objects.ComputerShop;
import data.objects.Device;
import data.people.Customer;
import gui.employee.ControllerEmployee;
import gui.employee.GuiEmployee;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class GuiCustomer extends Application {

    private Socket socket;
    private String host = "localhost";
    private int port = 10000;
    private Boolean connected = false;
    private ControllerCustomer controllerCustomer;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private static GuiCustomer instance = null;
    private static Customer currentCustomer;
    public static String ClientType = "CUSTOMER";
    public static String BuyItem = "BOUGHT ITEM";
    private static ArrayList<Device> devices;

    @FXML private Button btnAddToCart = new Button();

    @FXML private ComboBox ComboBoxDevices = new ComboBox();

    synchronized public static GuiCustomer getInstance(){
        if (instance == null){
            instance = new GuiCustomer();
        }
        return instance;
    }

    @Override
    public void start(Stage stage) throws Exception {
        devices = new ArrayList<>();
        currentCustomer = new Customer();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./GuiCustomer.fxml"));
        Parent root = fxmlLoader.load();
//        controllerCustomer = fxmlLoader.getController();
        stage.setTitle("Computershop: Customer Portal");
        stage.setScene(new Scene(root, root.prefWidth(600), root.prefHeight(600)));
        stage.show();


        connect();
        setData();

//        new Thread(() -> {
//            connect();
//        }).start();
//        new Thread( ()->{
//            while (connected){
//                controllerCustomer.setData();
//            }
//        }).start();

    }

    public boolean connect() {
        try {
            this.connected = true;
            this.socket = new Socket(this.host, this.port);
            this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
            this.objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.dataInputStream = new DataInputStream(this.socket.getInputStream());
            this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());

            //Step 0: Show server details.
            String server = dataInputStream.readUTF();
            System.out.println(server);

            //Step 1: Send client type Data.
            this.dataOutputStream.writeUTF(GuiCustomer.ClientType);

//            Step 1.2: Get all the available devices.
            int listSize = Integer.parseInt(dataInputStream.readUTF());
            devices.clear();
            for (int i = 0; i < listSize; i++){
                devices.add((Device) objectInputStream.readObject());
            }



            //Step 1: Log into server //TODO: Errors when user is not known.
//            dataOutputStream.writeUTF();
//            dataOutputStream.writeUTF();
//            System.out.println(dataInputStream.readUTF());

            this.socket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public void sentAllDevicesInArray(ObjectOutputStream objectOutputStream, DataOutputStream dataOutputStream, ArrayList arrayList) throws IOException {

        dataOutputStream.writeUTF("DEVICEDATASETCHANGED");
        dataOutputStream.writeUTF(arrayList.size()+"");

        for (Object object : arrayList){
            objectOutputStream.writeObject(object);
        }
    }


    public void setData(){
        for (Device device : getDevices()){
            ComboBoxDevices.getItems().add(device);
            ComboBoxDevices.setCellFactory(param -> new ListCell<Device>(){
                @Override
                protected void updateItem(Device item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null || item.getDeviceID() == null){
                        setText(null);
                    }else {
                        setText(item.printDevice());
                    }
                }
            });
        }
    }

    @FXML
    public void btnAddToCartClick(){
        try {
            dataOutputStream.writeUTF(BuyItem);
            objectOutputStream.writeObject(controllerCustomer.ComboBoxDevices.getValue());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        Device deviceToBuy = null;
        Device device = (Device) ComboBoxDevices.getValue();
        for (Device device1 : GuiCustomer.getInstance().getDevices()){
            if (device1.equals(device)){
                deviceToBuy = device1;
            }
        }
        devices.remove(deviceToBuy);
        currentCustomer.getShoppincart().add(deviceToBuy);
        System.out.println(controllerCustomer.ComboBoxDevices.getValue());
        System.out.println(currentCustomer.getShoppincart().size());
    }
}
