package gui.employee;

import data.objects.Device;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerEmployee implements Initializable {

    private GuiEmployee guiEmployee = null;

    @FXML
    Label labelText;

    @FXML
    TextField textFieldUserName;

    @FXML
    TextField textFieldPassword;

    @FXML
    Button btnLogin;

    @FXML
    public void onButtonLoginClick(){
        System.out.println(textFieldUserName.getText());
        System.out.println(textFieldPassword.getText());
        guiEmployee.connect();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        guiEmployee = GuiEmployee.getInstance();
    }

    public void setData(){
    }
}
