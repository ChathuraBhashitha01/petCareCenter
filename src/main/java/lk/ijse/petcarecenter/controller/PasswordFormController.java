package lk.ijse.petcarecenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.petcarecenter.model.PasswordModel;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PasswordFormController implements Initializable {
    public Button btnLogin;
    public Button btnChangePassword;
    public AnchorPane root;
    public TextField txtUserName;
    public TextField txtPassword;
    public ComboBox cmbUser;
    private String[] user = {"Manager", "Receptionist"};

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        String user= (String) cmbUser.getValue();

            ResultSet resultSet = PasswordModel.search(user);
            if (resultSet.next()) {
              String  userName = resultSet.getString(2);
              String  password=resultSet.getString(3);

                if(cmbUser.getValue().equals("Receptionist")){
                    if (cmbUser.getValue().equals("Receptionist") && txtUserName.getText().equals(userName) && txtPassword.getText().equals(password)) {
                        Stage stage = (Stage) root.getScene().getWindow();
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/receptionistDashBoard_form.fxml"))));
                        stage.setTitle("Receptionist Dash Board");
                        stage.centerOnScreen();
                        stage.show();
                    }else {
                        new Alert(Alert.AlertType.CONFIRMATION, "Wrong Receptionist Login Field:)").show();
                    }
                }
                if(cmbUser.getValue().equals("Manager")) {
                    if (cmbUser.getValue().equals("Manager") && txtUserName.getText().equals(userName) && txtPassword.getText().equals(password)) {
                        Stage stage = (Stage) root.getScene().getWindow();
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/managerDashBoard_form.fxml"))));
                        stage.setTitle("Manage Dash Board");
                        stage.centerOnScreen();
                        stage.show();
                    } else {
                        new Alert(Alert.AlertType.CONFIRMATION, "Wrong Manager Login Field:)").show();
                    }
                }
            }
//        Stage stage = (Stage) root.getScene().getWindow();
//        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/managerDashBoard_form.fxml"))));
//        stage.setTitle("Manage");
//        stage.centerOnScreen();
//        stage.show();
    }

    public void btnChangePasswordOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/addPassword_form.fxml"))));
        stage.setTitle("Manage");
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbUser.getItems().addAll(user);
    }
}
