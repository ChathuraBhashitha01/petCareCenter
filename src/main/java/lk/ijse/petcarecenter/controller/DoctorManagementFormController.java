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
import lk.ijse.petcarecenter.dto.Doctor;
import lk.ijse.petcarecenter.dto.tm.DoctorTM;
import lk.ijse.petcarecenter.model.DoctorModel;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DoctorManagementFormController implements Initializable {
    public AnchorPane root;
    public Button btnBack;
    public TextField txtDoctorId;
    public TextField txtDoctorName;
    public TextField txtContact;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelect;
    public TableView tblDoctor;
    public TableColumn colDoctorId;
    public TableColumn colName;
    public TableColumn colContact;

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
    private void generateNextDoctorId() {
        try {
            String id = DoctorModel.getNextDoctorId();
            txtDoctorId.setText(id);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void txtDoctorIdOnAction(ActionEvent actionEvent) {
        try {
            Doctor doctor = DoctorModel.search(txtDoctorId.getText());
            if (doctor != null) {
                txtDoctorId.setText(doctor.getDoctorID());
                txtDoctorName.setText(doctor.getName());
                txtContact.setText(String.valueOf(doctor.getPhoneNumber()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }
    }

    public void txtDoctorNameOnAction(ActionEvent actionEvent) {

    }

    public void txtContactOnAction(ActionEvent actionEvent) {

    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        String id=txtDoctorId.getText();
        boolean isTrue= DoctorModel.seachID(id);
        if (isTrue==true) {
            new Alert(Alert.AlertType.CONFIRMATION, "Doctor ID already added :)").show();
        }

        if (txtDoctorId.getText().isEmpty()||txtDoctorName.getText().isEmpty()||txtContact.getText().isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "All Field Are Required:)").show();
        }
        if(isTrue==false){
            try {
                String name = txtDoctorName.getText();
                int contact = Integer.parseInt(txtContact.getText());

                Doctor doctor = new Doctor(id, name, contact);
                boolean isSaved = DoctorModel.add(doctor);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Appointment added :)").show();
                }
            }catch (Exception e){

            }
        }
        generateNextDoctorId();
        txtDoctorName.clear();
        txtContact.clear();
        setCellValueFactory();
        getAll();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {

        if (txtDoctorId.getText().isEmpty()||txtDoctorName.getText().isEmpty()||txtContact.getText().isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "All Field Are Required:)").show();
        } else {
            String id = txtDoctorId.getText();
            String name = txtDoctorName.getText();
            int contact = Integer.parseInt(txtContact.getText());

            Doctor doctor=new Doctor(id,name,contact);
            boolean isSaved= DoctorModel.update(doctor);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Appointment Updated :)").show();
            }
            generateNextDoctorId();
            txtDoctorName.clear();
            txtContact.clear();
            setCellValueFactory();
            getAll();
        }

    }

    public void btnDelectOnAction(ActionEvent actionEvent) throws SQLException {
        if(txtDoctorId.getText().isEmpty()){
            new Alert(Alert.AlertType.CONFIRMATION, "Doctor ID Field Are Required:)").show();
        }else {
            String id = txtDoctorId.getText();

            Doctor doctor=new Doctor(id,null,0);
            boolean isSaved= DoctorModel.delete(doctor);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Appointment deletd :)").show();
            }
            generateNextDoctorId();
            txtDoctorName.clear();
            txtContact.clear();
            setCellValueFactory();
            getAll();
        }
    }

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
        generateNextDoctorId();
    }
    private void getAll() {
        try {
            ObservableList<DoctorTM> obList = FXCollections.observableArrayList();
            List<Doctor> cusList = DoctorModel.getAll();

            for (Doctor doctor : cusList) {
                obList.add(new DoctorTM(
                        doctor.getDoctorID(),
                        doctor.getName(),
                        doctor.getPhoneNumber()
                ));
            }
            tblDoctor.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void setCellValueFactory() {
        colDoctorId.setCellValueFactory(new PropertyValueFactory<>("doctorID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }
}
