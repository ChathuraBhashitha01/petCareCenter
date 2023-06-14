package lk.ijse.petcarecenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerDashBoardFormController {
    public Button btnPetRegistration;
    public Button btnSalaryManagement;
    public Button btnAppointment;
    public Button btnScheduleManagement;
    public Button btnPetsManagement;
    public Button btnMedicineManagement;
    public Button btnPetItemManagement;
    public Button btnDoctorsManagement;
    public Button btnPaymant;
    public Button btnReportManagement;
    public AnchorPane root;
    public Button btnBack;

    public void btnPetRegistrationOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/petRegistration_form.fxml"))));
        stage.setTitle("PetRegistration Form");
        stage.centerOnScreen();
        stage.show();
        backButtonController.backButton = 1;
    }

    public void btnAppointmentOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/appointment_form.fxml"))));
        stage.setTitle("Appointment Form");
        stage.centerOnScreen();
        stage.show();
        backButtonController.backButton = 1;
    }

    public void btnScheduleManagementOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/schdule_form.fxml"))));
        stage.setTitle("Schedule Form");
        stage.centerOnScreen();
        stage.show();
        backButtonController.backButton = 1;
    }

    public void btnPetsManagementOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/petManagement_form.fxml"))));
        stage.setTitle("PetsManagement Form");
        stage.centerOnScreen();
        stage.show();
        backButtonController.backButton = 1;

    }

    public void btnMedicineManagementOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/medicineManagement_form.fxml"))));
        stage.setTitle("Medicine Form");
        stage.centerOnScreen();
        stage.show();
        backButtonController.backButton = 1;

    }

    public void btnPetItemManagementOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/petsItemManagement_form.fxml"))));
        stage.setTitle("Pet Item Form");
        stage.centerOnScreen();
        stage.show();
        backButtonController.backButton = 1;

    }

    public void btnDoctorsManagementOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/doctorManagement_form.fxml"))));
        stage.setTitle("Doctor Form");
        stage.centerOnScreen();
        stage.show();
        backButtonController.backButton = 1;

    }

    public void btnPaymantOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/payment_form.fxml"))));
        stage.setTitle("Payment Form");
        stage.centerOnScreen();
        stage.show();
        backButtonController.backButton = 1;

    }

    public void btnReportManagementOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/reportManagement_form.fxml"))));
        stage.setTitle("Report Form");
        stage.centerOnScreen();
        stage.show();
        backButtonController.backButton = 1;
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/password_form.fxml"))));
        stage.setTitle("Manager Dash Board");
        stage.centerOnScreen();
        stage.show();
    }
}
