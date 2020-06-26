package gui.server;

import data.objects.Device;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import server.ShopServer;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerServer implements Initializable {

    private GuiServer guiServer = null;
    private ShopServer shopServer = null;
    private Boolean running = false;

    @FXML
    Label labelServerProg;

    @FXML
    Button btnStartServer;

    @FXML
    public void onButtonOptionServerClick(){
        if (running){
            shopServer.stop();
            btnStartServer.setText("Start Server");
            labelServerProg.setText("Server has stopped...");
            running = false;
        }else {
            labelServerProg.setText("Server is starting...");
            shopServer.start();
            labelServerProg.setText("Server is running...");
            btnStartServer.setText("Stop Server");
            running = true;
        }
    }

    @FXML
    public void onButtonRefreshClick(){
        setData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        guiServer = GuiServer.getInstance();
        shopServer = GuiServer.getShopServer();
    }

    public void setData(){
    }
}
