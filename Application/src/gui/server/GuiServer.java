package gui.server;

import data.objects.ComputerShop;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.ShopServer;

public class GuiServer extends Application {

    private static GuiServer instance = null;
    private static ShopServer shopServer;

    synchronized public static GuiServer getInstance(){
        if (instance == null){
            instance = new GuiServer();
        }
        return instance;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.shopServer = new ShopServer(10000);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./GuiServer.fxml"));
        Parent root = fxmlLoader.load();
        ControllerServer controllerServer = fxmlLoader.getController();

        stage.setTitle("Computershop: Server Portal");
        stage.setScene(new Scene(root, root.prefWidth(600), root.prefHeight(500)));
        stage.show();

        controllerServer.setData();
    }

    public static ShopServer getShopServer() {
        return shopServer;
    }
}
