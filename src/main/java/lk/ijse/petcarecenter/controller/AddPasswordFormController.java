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
import lk.ijse.petcarecenter.dto.AddPassword;
import lk.ijse.petcarecenter.model.AddPasswordModel;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddPasswordFormController implements Initializable {

    public Button btnChangePassword;
    public ComboBox cmbUser;
    public TextField txtUserName;
    public TextField txtPassword;
    public TextField txtNewUserName;
    public TextField txtNewPassword;
    public Button btnBack;
    public AnchorPane root;
    private String[] user = {"Manager", "Receptionist"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbUser.getItems().addAll(user);
    }

    public void btnChangePasswordOnAction(ActionEvent actionEvent) throws SQLException {
        String user = (String) cmbUser.getValue();
        String userName=null;
        String password=null;

        ResultSet resultSet = AddPasswordModel.select(user);
        if (resultSet.next()) {
            userName = resultSet.getString(1);
            password = resultSet.getString(2);
        }
        if (txtUserName.getText().isEmpty()||txtPassword.getText().isEmpty()||((String) cmbUser.getValue()).isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "All Field Are Required:)").show();
        } else {
            if (cmbUser.getValue().equals(user) && txtUserName.getText().equals(userName) && txtPassword.getText().equals(password)) {
                String newUserName = txtNewUserName.getText();
                String newPassword = txtNewPassword.getText();

                AddPassword addPassword=new AddPassword(user,newUserName,newPassword);
                boolean isUpdated = AddPasswordModel.update(addPassword);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "updated!!").show();
                }
            } else {
                new Alert(Alert.AlertType.CONFIRMATION,
                        "Wrong :)")
                        .show();
            }
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/password_form.fxml"))));
        stage.setTitle("Manage");
        stage.centerOnScreen();
        stage.show();
    }
}
