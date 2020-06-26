package gui.customer;

import data.objects.Device;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerCustomer implements Initializable {

    private GuiCustomer guiCustomer = null;

    @FXML
    ComboBox<Device> ComboBoxDevices;

    @FXML
    Label labelText;

    @FXML
    Button btnAddToCart;

    @FXML
    public Boolean onButtonAddToCartClick(){

            Device deviceToBuy = null;
            Device device = ComboBoxDevices.getValue();
            for (Device device1 : GuiCustomer.getInstance().getDevices()){
                if (device1.equals(device)){
                    deviceToBuy = device1;
                }
            }
            GuiCustomer.getInstance().getDevices().remove(deviceToBuy);
            GuiCustomer.getCurrentCustomer().getShoppincart().add(deviceToBuy);
        return true;
    }

    @FXML
    public void onButtonRefreshClick(){
        setData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        guiCustomer = GuiCustomer.getInstance();
    }

    public void setData(){
        for (Device device : GuiCustomer.getInstance().getDevices()){
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
}
