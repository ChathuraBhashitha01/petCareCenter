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
import lk.ijse.petcarecenter.dto.Schdule;
import lk.ijse.petcarecenter.dto.tm.SchduleTM;
import lk.ijse.petcarecenter.model.DoctorModel;
import lk.ijse.petcarecenter.model.SchduleModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class SchduleFormController implements Initializable {
    public AnchorPane root;
    public Button btnBack;
    public TextField txtSchduleId;
    public TextField txtDoctorId;
    public TextField txtDate;
    public TextField txtInTime;
    public TextField txtOutTime;
    public Button btnSave;
    public TableView tblSchdule;
    public TableColumn colSchduleId;
    public TableColumn colDoctorId;
    public TableColumn colDate;
    public TableColumn colInTime;
    public TableColumn colOutTime;
    public Button btnDelete;
    public Label lblDate;
    public Button btnUpdate;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
        setDate();
        generateNextSchduleId();
    }

    private void setDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
        txtDate.setText(String.valueOf(LocalDate.now()));
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
    private void generateNextSchduleId() {
        try {
            String id = SchduleModel.getNextSchduleId();
            txtSchduleId.setText(id);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void txtSchduleIdOnAction(ActionEvent actionEvent) {
        try {
            Schdule schdule = SchduleModel.search(txtSchduleId.getText());
            if (schdule != null) {
                txtSchduleId.setText(schdule.getSchduleID());
                txtDoctorId.setText(schdule.getDoctorID());
                txtDate.setText(schdule.getDate());
                txtInTime.setText(schdule.getInTime());
                txtOutTime.setText(schdule.getOutTime());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }


    }

    public void txtDoctorIdOnAction(ActionEvent actionEvent) {

    }

    public void txtDateOnAction(ActionEvent actionEvent) {

    }

    public void txtInTimeOnAction(ActionEvent actionEvent) {

    }

    public void txtOutTimeOnAction(ActionEvent actionEvent) {

    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        String doctorId = txtDoctorId.getText();
        String id = txtSchduleId.getText();

        boolean schduleIDTrue= SchduleModel.seachID(id);
        if (schduleIDTrue==true) {
            new Alert(Alert.AlertType.CONFIRMATION, "Schdule ID already Added :)").show();
        }

        boolean isTrue= DoctorModel.seachID(doctorId);
        if (isTrue==false) {
            new Alert(Alert.AlertType.CONFIRMATION, "Doctor ID not Founded :Wrong Doctor ID)").show();
        }
        if(txtSchduleId.getText().isEmpty()||txtDoctorId.getText().isEmpty()||txtInTime.getText().isEmpty()||txtOutTime.getText().isEmpty()||txtDate.getText().isEmpty()){
            new Alert(Alert.AlertType.CONFIRMATION, "All Field Are Required:)").show();
        }
        if(isTrue==true&&schduleIDTrue==false){
            String inTime = txtInTime.getText();
            String outTime = txtOutTime.getText();
            String date = txtDate.getText();


            Schdule schdule=new Schdule(id,inTime,outTime,date,doctorId);
            boolean isSaved= SchduleModel.add(schdule);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Schdule added :)").show();
            }
        }
        generateNextSchduleId();
        setCellValueFactory();
        getAll();
        txtDate.clear();
        txtInTime.clear();
        txtDoctorId.clear();
        txtOutTime.clear();
        setDate();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        if (txtSchduleId.getText().isEmpty()){
            new Alert(Alert.AlertType.CONFIRMATION, "SchduleID Field Are Required:)").show();
        }else {
            String id = txtSchduleId.getText();

            Schdule schdule=new Schdule(id,null,null,null,null);
            boolean isSaved= SchduleModel.delete(schdule);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Schdule deleted :)").show();
            }

            generateNextSchduleId();
            setCellValueFactory();
            getAll();
            txtDoctorId.clear();
            txtOutTime.clear();
            txtDate.clear();
            txtInTime.clear();
            setDate();
        }
    }
    private void getAll() {
        try {
            ObservableList<SchduleTM> obList = FXCollections.observableArrayList();
            List<Schdule> cusList = SchduleModel.getAll();

            for (Schdule schdule : cusList) {
                obList.add(new SchduleTM(
                        schdule.getSchduleID(),
                        schdule.getInTime(),
                        schdule.getOutTime(),
                        schdule.getDate(),
                        schdule.getDoctorID()
                ));
            }
            tblSchdule.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void setCellValueFactory() {
        colSchduleId.setCellValueFactory(new PropertyValueFactory<>("schduleID"));
        colInTime.setCellValueFactory(new PropertyValueFactory<>("inTime"));
        colOutTime.setCellValueFactory(new PropertyValueFactory<>("outTime"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDoctorId.setCellValueFactory(new PropertyValueFactory<>("doctorID"));


    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {

        String doctorId = txtDoctorId.getText();
        boolean isTrue= DoctorModel.seachID(doctorId);
        if (isTrue==false) {
            new Alert(Alert.AlertType.CONFIRMATION, "Doctor ID not Founded :Wrong Doctor ID)").show();
        }
        if(txtSchduleId.getText().isEmpty()||txtDoctorId.getText().isEmpty()||txtInTime.getText().isEmpty()||txtOutTime.getText().isEmpty()||txtDate.getText().isEmpty()){
            new Alert(Alert.AlertType.CONFIRMATION, "All Field Are Required:)").show();
        }
        if(isTrue==true){
            String id = txtSchduleId.getText();
            String inTime = txtInTime.getText();
            String outTime = txtOutTime.getText();
            String date = txtDate.getText();

            Schdule schdule=new Schdule(id,inTime,outTime,date,doctorId);
            boolean isSaved= SchduleModel.update(schdule);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Schdule updated :)").show();
            }
            generateNextSchduleId();
            setCellValueFactory();
            getAll();
            txtDoctorId.clear();
            txtOutTime.clear();
            txtDate.clear();
            txtInTime.clear();
            setDate();
        }

    }
}
