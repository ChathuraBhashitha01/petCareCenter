package lk.ijse.petcarecenter.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.petcarecenter.dto.Appointment;
import lk.ijse.petcarecenter.dto.tm.AppointmentTM;
import lk.ijse.petcarecenter.model.AppointmentModel;
import lk.ijse.petcarecenter.model.PetModel;
import lk.ijse.petcarecenter.model.SchduleModel;
import lk.ijse.petcarecenter.model.ServiceModel;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AppointmentFormController implements Initializable {
    public Button btnBack;
    public TextField txtAppoinmentId;
    public TextField txtSchduleId;
    public TextField txtServiceId;
    public TextField txtPetId;
    public AnchorPane root;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelect;
    public TableView tblAppointment;
    public TableColumn colAppointmentId;
    public TableColumn colPetId;
    public TableColumn colSchduleId;
    public TableColumn colServiceId;
    public TableColumn colDate;
    public TextField txtDate;
    public Label lblDate;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
        setDate();
        generateNextAddointmentId();
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        if(backButtonController.backButton==1){
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/managerDashBoard_form.fxml"))));
            stage.setTitle("Manage");
            stage.centerOnScreen();
            stage.show();
        }else{
            System.out.println("employee");
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/receptionistDashBoard_form.fxml"))));
            stage.setTitle("Manage");
            stage.centerOnScreen();
            stage.show();
        }


    }
    private void generateNextAddointmentId() {
        try {
            String id = AppointmentModel.getNextAppointmentId();
            txtAppoinmentId.setText(id);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }


    public void txtAppoinmentIdOnAction(ActionEvent actionEvent) {
        try {
//            Appointment appointment = AppointmentModel.search(txtAppoinmentId.getText());
//            if (appointment != null) {
//                txtAppoinmentId.setText(appointment.getAppointmentID());
//                txtPetId.setText(appointment.getPetID());
//                txtSchduleId.setText(appointment.getSchduleID());
//                txtDate.setText(appointment.getDate());
//                txtServiceId.setText(appointment.getServiceID());
//
//            }
            ResultSet resultSet=AppointmentModel.search(txtAppoinmentId.getText());
            if (resultSet.next()){
                txtAppoinmentId.setText(resultSet.getString(1));
                txtPetId.setText(resultSet.getString(2));
                txtSchduleId.setText(resultSet.getString(3));
                txtDate.setText(resultSet.getString(4));
                txtServiceId.setText(resultSet.getString(5));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }

    }

    public void txtSchduleIdOnAction(ActionEvent actionEvent) {

    }

    public void txtServiceIdOnAction(ActionEvent actionEvent) {

    }

    public void txtPetIdOnAction(ActionEvent actionEvent) {

    }
    private void setDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
        txtDate.setText(String.valueOf(LocalDate.now()));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        String appointmentID=  txtAppoinmentId.getText();
        String petID=txtPetId.getText();
        String schduleID=txtSchduleId.getText();
        String serviceID=txtServiceId.getText();

        boolean appointmantIDTrue= AppointmentModel.seachID(appointmentID);
        if (appointmantIDTrue==true) {
            new Alert(Alert.AlertType.CONFIRMATION, "Appointment ID already added:)").show();
        }
        boolean petIDTrue= PetModel.seachID(petID);
        if (petIDTrue==false) {
            new Alert(Alert.AlertType.CONFIRMATION, "Pet ID not Founded :Wrong Pet ID)").show();
        }
        boolean schduleIDTrue= SchduleModel.seachID(schduleID);
        if (schduleIDTrue==false) {
            new Alert(Alert.AlertType.CONFIRMATION, "Schdule ID not Founded :Wrong Schdule ID)").show();
        }
        boolean serviceIDTrue= ServiceModel.seachID(serviceID);
        if (serviceIDTrue==false) {
            new Alert(Alert.AlertType.CONFIRMATION, "Service ID not Founded :Wrong Service ID)").show();
        }

        if (txtAppoinmentId.getText().isEmpty()||txtPetId.getText().isEmpty()||txtSchduleId.getText().isEmpty()||txtServiceId.getText().isEmpty()||txtDate.getText().isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "All Field Are Required:)").show();
        }
        if(petIDTrue==true&&schduleIDTrue==true&&serviceIDTrue==true&&appointmantIDTrue==false){
            String date=txtDate.getText();

            Appointment appointment=new Appointment(appointmentID,petID,schduleID,date,serviceID);
            boolean isSaved= AppointmentModel.add(appointment);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Appointment added :)").show();
            }
        }
        generateNextAddointmentId();
        setCellValueFactory();
        getAll();
        txtPetId.clear();
        txtSchduleId.clear();
        txtServiceId.clear();
        setDate();
    }
    public void btnDelectOnAction(ActionEvent actionEvent) throws SQLException {
        if (txtAppoinmentId.getText().isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "All Field Are Required:)").show();
        } else {
            String appointmentID = txtAppoinmentId.getText();
            Appointment appointment=new Appointment(appointmentID,null,null,null,null);
            boolean isSaved= AppointmentModel.delect(appointment);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Appointment added :)").show();
            }

            generateNextAddointmentId();
            setCellValueFactory();
            getAll();
            txtPetId.clear();
            txtSchduleId.clear();
            txtServiceId.clear();
            setDate();
        }
    }
    private void getAll()  {

        try {
            ObservableList<AppointmentTM> obList = FXCollections.observableArrayList();
            List<Appointment> cusList = AppointmentModel.getAll();

            for (Appointment appointment : cusList) {
                obList.add(new AppointmentTM(
                        appointment.getAppointmentID(),
                        appointment.getPetID(),
                        appointment.getSchduleID(),
                        appointment.getDate(),
                        appointment.getServiceID()

                ));
            }
            tblAppointment.refresh();
            tblAppointment.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void setCellValueFactory() {
        colAppointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        colPetId.setCellValueFactory(new PropertyValueFactory<>("petID"));
        colSchduleId.setCellValueFactory(new PropertyValueFactory<>("schduleID"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colServiceId.setCellValueFactory(new PropertyValueFactory<>("serviceID"));

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        String petID=txtPetId.getText();
        String schduleID=txtSchduleId.getText();
        String serviceID=txtServiceId.getText();

        boolean petIDTrue= PetModel.seachID(petID);
        if (petIDTrue==false) {
            new Alert(Alert.AlertType.CONFIRMATION, "Pet ID not Founded :Wrong Pet ID)").show();
        }
        boolean schduleIDTrue= SchduleModel.seachID(schduleID);
        if (schduleIDTrue==false) {
            new Alert(Alert.AlertType.CONFIRMATION, "Schdule ID not Founded :Wrong Schdule ID)").show();
        }
        boolean serviceIDTrue= ServiceModel.seachID(serviceID);
        if (serviceIDTrue==false) {
            new Alert(Alert.AlertType.CONFIRMATION, "Service ID not Founded :Wrong Service ID)").show();
        }

        if (txtAppoinmentId.getText().isEmpty()||txtPetId.getText().isEmpty()||txtDate.getText().isEmpty()||txtSchduleId.getText().isEmpty()||txtServiceId.getText().isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "All Field Are Required:)").show();
        }
        if (petIDTrue==true&&schduleIDTrue==true&&serviceIDTrue==true){
            String appointmentID=  txtAppoinmentId.getText();
            String date=txtDate.getText();
            Appointment appointment=new Appointment(appointmentID,petID,schduleID,date,serviceID);
            boolean isSaved= AppointmentModel.update(appointment);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Appointment updated! :)").show();
            }
            setCellValueFactory();
            getAll();
            generateNextAddointmentId();
            txtServiceId.clear();
            txtDate.clear();
            txtSchduleId.clear();
            txtPetId.clear();
        }

    }

}
