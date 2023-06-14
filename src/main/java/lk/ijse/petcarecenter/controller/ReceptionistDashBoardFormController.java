package lk.ijse.petcarecenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ReceptionistDashBoardFormController {
    public Button btnPetRegistration;
    public Button btnAppointment;
    public Button btnScheduleManagement;
    public Button btnPetsManagement;
    public Button btnPetItemManagement;
    public Button btnPayment;
    public AnchorPane root;
    public int backButton=1;
    public Button btnBack;

    public void backButtonManager(){

    }

    public void btnPetRegistrationOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/petRegistration_form.fxml"))));
        stage.setTitle("Pet Registration Form");
        stage.centerOnScreen();
        stage.show();

    }

    public void btnAppointmentOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/appointment_form.fxml"))));
        stage.setTitle("Appointment Form");
        stage.centerOnScreen();
        stage.show();

    }

    public void btnScheduleManagementOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/schdule_form.fxml"))));
        stage.setTitle("Schedule Form");
        stage.centerOnScreen();
        stage.show();
    }

    public void btnPetsManagementOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/petManagement_form.fxml"))));
        stage.setTitle("PetsManagement Form");
        stage.centerOnScreen();
        stage.show();

    }

    public void btnPetItemManagementOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/petsItemManagement_form.fxml"))));
        stage.setTitle("Pet Item Form");
        stage.centerOnScreen();
        stage.show();

    }

    public void btnPaymantOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/payment_form.fxml"))));
        stage.setTitle("Payment Form");
        stage.centerOnScreen();
        stage.show();

    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/password_form.fxml"))));
        stage.setTitle("Receptionist Dash Board");
        stage.centerOnScreen();
        stage.show();
    }
}
