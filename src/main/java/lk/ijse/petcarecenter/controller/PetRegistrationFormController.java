package lk.ijse.petcarecenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.petcarecenter.dto.Owner;
import lk.ijse.petcarecenter.dto.Pet;
import lk.ijse.petcarecenter.model.OwnerModel;
import lk.ijse.petcarecenter.model.PetModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PetRegistrationFormController implements Initializable {
    public AnchorPane root;
    public Button btnBack;
    public ComboBox<String> txtPetType;
    public TextField txtPetName;
    public TextField txtPetId;
    public TextField txtPetAge;
    public TextField txtCustomerId;
    public TextField txtCustomerName;
    public TextField txtContact;
    public TextField txtBreed;
    public Button btnSave;
    public Button btnCustomerSave;
    public TextField txtNewCustomerId;
    public Label lblPetId;
    public Label lblOwnerId;
    public Button btnPetSave;

    private String[] prtType = {"dog", "cat", "birds"};

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
    private void generateNextPetId() {
        try {
            String id = PetModel.getNextPetId();
            txtPetId.setText(id);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    private void generateNextOwnerId() {
        try {
            String id = PetModel.getNextOwnerId();
            txtNewCustomerId.setText(id);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void txtPetTypeOnAction(ActionEvent actionEvent) {

    }

    public void txtPetNameOnAction(ActionEvent actionEvent) {

    }

    public void txtPetIdOnAction(ActionEvent actionEvent) {

    }

    public void txtPetAgeOnAction(ActionEvent actionEvent) {

    }

    public void txtCustomerIdOnaction(ActionEvent actionEvent) {

    }

    public void txtCustomerNameOnAction(ActionEvent actionEvent) {

    }

    public void txtContactOnAction(ActionEvent actionEvent) {

    }

    public void txtBreedOnAction(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtPetType.getItems().addAll(prtType);
        generateNextPetId();
        generateNextOwnerId();
    }
    public void btnCustomerSaveOnAction(ActionEvent actionEvent) throws SQLException {
        String newOwnerId = txtNewCustomerId.getText();

        boolean ownerIDTrue=OwnerModel.seachID(newOwnerId);
        if (ownerIDTrue==true) {
            new Alert(Alert.AlertType.CONFIRMATION, "Owner ID already Added :)").show();
        }
        if (txtNewCustomerId.getText().isEmpty()||txtCustomerName.getText().isEmpty()){
            new Alert(Alert.AlertType.CONFIRMATION, "All Field Are Required:)").show();
        }
        if (ownerIDTrue==false){
            try {
                String ownerName = txtCustomerName.getText();
                int contact = Integer.parseInt(txtContact.getText());

                Owner owner = new Owner(newOwnerId, ownerName, contact);
                boolean isSaved = OwnerModel.add(owner);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Medicine added :)").show();
                }
            }catch (Exception e){

            }

        }
        generateNextOwnerId();
        txtCustomerName.clear();
        txtContact.clear();

    }

    public void btnPetSaveOnAction(ActionEvent actionEvent) throws SQLException {
        String ownerId = txtCustomerId.getText();
        boolean ownerIDTrue=OwnerModel.seachID(ownerId);
        if (ownerIDTrue==false) {
            new Alert(Alert.AlertType.CONFIRMATION, "Owner ID not Founded :Wrong Pet ID)").show();
        }
        String id = txtPetId.getText();
        boolean isTrue=PetModel.seachID(id);
        if (isTrue==true) {
            new Alert(Alert.AlertType.CONFIRMATION, "Pet ID already Added :)").show();
        }
        if (txtPetId.getText().isEmpty()||txtPetName.getText().isEmpty()||txtPetAge.getText().isEmpty()||txtPetType.getValue().isEmpty()||txtBreed.getText().isEmpty()||txtCustomerId.getText().isEmpty()){
            new Alert(Alert.AlertType.CONFIRMATION, "All Field Are Required:)").show();
        }
        if(ownerIDTrue==true&&isTrue==false) {
            String name = txtPetName.getText();
            String type = txtPetType.getValue();
            String breed = txtBreed.getText();
            int age = Integer.parseInt(txtPetAge.getText());


            Pet pet=new Pet(id,name,age,type,breed,ownerId);
            boolean isSaved= PetModel.add(pet);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Pet added :)").show();
            }

            generateNextPetId();
            txtPetId.clear();
            txtPetName.clear();
            txtPetAge.clear();
            txtContact.clear();
            txtCustomerId.clear();
            txtCustomerId.clear();
            txtBreed.clear();
            txtCustomerName.clear();
        }

    }
}
