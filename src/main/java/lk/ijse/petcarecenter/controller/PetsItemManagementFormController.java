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
import lk.ijse.petcarecenter.dto.PetItem;
import lk.ijse.petcarecenter.dto.tm.PetItemTM;
import lk.ijse.petcarecenter.model.PetItemModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PetsItemManagementFormController implements Initializable {
    public AnchorPane root;
    public Button btnBack;
    public TextField txtItemCode;
    public TextField txtItemName;
    public TextField txtQuantity;
    public Button btnSave;
    public TableView tblPetItem;
    public TableColumn collemCode;
    public TableColumn colName;
    public TableColumn colQuantity;
    public Button btnDelete;
    public TextField txtPrice;
    public TableColumn colPrice;
    public Button btnUpdate;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
        generateNextItemId();
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        if(backButtonController.backButton==1){
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/managerDashBoard_form.fxml"))));
            stage.setTitle("Manager Dash Board");
            stage.centerOnScreen();
            stage.show();
        }else{
            System.out.println("employee");
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/receptionistDashBoard_form.fxml"))));
            stage.setTitle("Receptionist Dash Board");
            stage.centerOnScreen();
            stage.show();
        }

    }
    private void generateNextItemId() {
        try {
            String id = PetItemModel.getNextItemId();
            txtItemCode.setText(id);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void txtItemCodeOnAction(ActionEvent actionEvent) {
        try {
            PetItem petItem = PetItemModel.searchById(txtItemCode.getText());
            if (petItem != null) {
                txtItemCode.setText(petItem.getItemCode());
                txtItemName.setText(petItem.getName());
                txtPrice.setText(String.valueOf(petItem.getPrice()));
                txtQuantity.setText(String.valueOf(petItem.getQuantity()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened!").show();
        }

    }

    public void txtItemNameOnAction(ActionEvent actionEvent) {

    }

    public void txtQuantityOnAction(ActionEvent actionEvent) {

    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {

        String id = txtItemCode.getText();
        boolean isTrue= PetItemModel.seachID(id);
        if (isTrue==true) {
            new Alert(Alert.AlertType.CONFIRMATION, "Pet Item Code already added:)").show();
        }

        if(txtItemCode.getText().isEmpty()||txtItemName.getText().isEmpty()||txtQuantity.getText().isEmpty()||txtPrice.getText().isEmpty()){
            new Alert(Alert.AlertType.CONFIRMATION, "All Field Are Required:)").show();
        }
        if(isTrue==false){
            try {
                String name = txtItemName.getText();
                int quantity = Integer.parseInt(txtQuantity.getText());
                double price = Double.parseDouble(txtPrice.getText());

                PetItem petItem = new PetItem(id, name, price, quantity);
                boolean isSaved = PetItemModel.add(petItem);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Pet Item added :)").show();
                }
            }catch (Exception e){

            }
        }
        setCellValueFactory();
        getAll();
        generateNextItemId();
        txtItemName.clear();
        txtQuantity.clear();
        txtPrice.clear();
    }

    private void getAll() {
        try {
            ObservableList<PetItemTM> obList = FXCollections.observableArrayList();
            List<PetItem> cusList = PetItemModel.getAll();

            for (PetItem petItem : cusList) {
                obList.add(new PetItemTM(
                        petItem.getItemCode(),
                        petItem.getName(),
                        petItem.getPrice(),
                        petItem.getQuantity()
                ));
            }
            tblPetItem.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void setCellValueFactory() {
        collemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        if(txtItemCode.getText().isEmpty()){
            new Alert(Alert.AlertType.CONFIRMATION, "All Field Are Required:)").show();
        }else {
            String id = txtItemCode.getText();

            PetItem petItem=new PetItem(id,null,0,0);
            boolean isSaved= PetItemModel.delete(petItem);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Pet Item deletd:)").show();
            }

            setCellValueFactory();
            getAll();
            generateNextItemId();
            txtItemName.clear();
            txtQuantity.clear();
            txtPrice.clear();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        if (txtItemCode.getText().isEmpty()||txtItemName.getText().isEmpty()||txtQuantity.getText().isEmpty()||txtPrice.getText().isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "All Field Are Required:)").show();
        } else {
            String id = txtItemCode.getText();
            String name = txtItemName.getText();
            int quantity = Integer.parseInt(txtQuantity.getText());
            double price = Double.parseDouble(txtPrice.getText());

            PetItem petItem=new PetItem(id,name,price,quantity);
            boolean isSaved= PetItemModel.update(petItem);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Pet Item updated:)").show();
            }

            setCellValueFactory();
            getAll();
            generateNextItemId();
            txtItemName.clear();
            txtQuantity.clear();
            txtPrice.clear();
        }

    }
}
