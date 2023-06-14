package lk.ijse.petcarecenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.petcarecenter.dto.Owner;
import lk.ijse.petcarecenter.dto.Pet;
import lk.ijse.petcarecenter.model.OwnerModel;
import lk.ijse.petcarecenter.model.PetModel;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PetManagementFormController {
    public Button btnBack;
    public TextField txtPetId;
    public Button btnSearch;
    public ComboBox txtPetType;
    public TextField txtPetName;
    public TextField txtPetAge;
    public TextField txtCustomerId;
    public TextField txtCustomerName;
    public TextField txtContact;
    public TextField txtBreed;
    public Button btnUpdate;
    public Button btnClear;
    public AnchorPane root;
    public TextField txtPettype;
    public Button btnDelete;

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

    public void txtPetIdOnAction(ActionEvent actionEvent) {

    }

    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtPetId.getText();
        boolean isTrue=PetModel.seachID(id);
        if (isTrue==false) {
            new Alert(Alert.AlertType.CONFIRMATION, "Pet ID not Founded :Wrong Pet ID)").show();
        }
        if (txtPetId.getText().isEmpty()){
            new Alert(Alert.AlertType.CONFIRMATION, "Pet ID Field Are Required:)").show();
        }
        if(isTrue==true){
            String ownerId = null;

            ResultSet resultSet=PetModel.search(id);
            if (resultSet.next()) {
                String pet_id = resultSet.getString(1);
                String pet_name = resultSet.getString(2);
                int age = resultSet.getInt(3);
                String type = resultSet.getString(5);
                String breed = resultSet.getString(6);
                ownerId = resultSet.getString(7);

                txtPetId.setText(pet_id);
                txtPetName.setText(pet_name);
                txtPetAge.setText(String.valueOf(age));
                txtBreed.setText(String.valueOf(breed));
                txtPettype.setText(type);
            }

            ResultSet ownerResultset= OwnerModel.search(ownerId);
            if (ownerResultset.next()) {
                String cus_id = ownerResultset.getString(1);
                String cus_name = ownerResultset.getString(2);
                int contact = ownerResultset.getInt(3);

                txtCustomerId.setText(ownerId);
                txtCustomerName.setText(cus_name);
                txtContact.setText(String.valueOf(contact));
            }
        }
    }

    public void txtPetNameOnAction(ActionEvent actionEvent) {

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

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        String petId = txtPetId.getText();
        String ownerId = txtCustomerId.getText();

        boolean petIDTrue=PetModel.seachID(petId);
        if (petIDTrue==false) {
            new Alert(Alert.AlertType.CONFIRMATION, "Pet ID not Founded :Wrong Pet ID)").show();
        }
        boolean ownerIDTrue=OwnerModel.seachID(ownerId);
        if (ownerIDTrue==false) {
            new Alert(Alert.AlertType.CONFIRMATION, "Owner ID not Founded :Wrong Owner ID)").show();
        }
        if (txtPetId.getText().isEmpty()||txtPetName.getText().isEmpty()||txtPetAge.getText().isEmpty()||txtBreed.getText().isEmpty()||txtCustomerId.getText().isEmpty()||txtCustomerName.getText().isEmpty()){
            new Alert(Alert.AlertType.CONFIRMATION, "All Field Are Required:)").show();
        }
        if(petIDTrue==true&&ownerIDTrue==true){
            String petName = txtPetName.getText();
            int age = Integer.parseInt(txtPetAge.getText());
            String type = txtPettype.getText();
            String breed = txtBreed.getText();
            String ownerName = txtCustomerName.getText();
            int contact = Integer.parseInt(txtContact.getText());


            Pet pet=new Pet(petId,petName,age,type,breed,ownerId);
            boolean isUpdated= PetModel.update(pet);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Pet Updated :)").show();
            }

            Owner owner=new Owner(ownerId,ownerName,contact);
            boolean isOwnerUpdated= OwnerModel.update(owner);
            if (isOwnerUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Owner Updated :)").show();
            }
        }

    }

    public void btnClearOnAction(ActionEvent actionEvent){
        txtPetId.clear();
        txtPetAge.clear();
        txtPetName.clear();
        txtPettype.clear();
        txtBreed.clear();
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtContact.clear();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent)throws SQLException {
        if (txtPetId.getText().isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "Pet ID Field Are Required:)").show();
        }else {
            String petId = txtPetId.getText();
            String ownerId = txtCustomerId.getText();

            Pet pet = new Pet(petId, null, 0, null, null, null);
            boolean isSaved = PetModel.delect(pet);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Pet deletd!").show();
            }
        }
    }
}
