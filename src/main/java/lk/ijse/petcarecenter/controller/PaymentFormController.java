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
import lk.ijse.petcarecenter.dto.*;
import lk.ijse.petcarecenter.dto.tm.ItemCartTM;
import lk.ijse.petcarecenter.dto.tm.MedicineCartTM;
import lk.ijse.petcarecenter.dto.tm.ServiceCartTM;
import lk.ijse.petcarecenter.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class PaymentFormController implements Initializable {

    public Button btnBack;
    public Label lblPetId;
    public Label lblPrice;
    public TextField txtPetId;
    public Button btnPetAdd;
    public Button btnPriceAdd;
    public Button btnClear;
    public Button btnPrint;
    public AnchorPane root;
    public Label lblOwnerId;
    public Label lblPetName;
    public ComboBox cmbItemCode;
    public ComboBox cmbServiceId;
    public ComboBox cmbMedicineId;
    public Label lblIemDescription;
    public Label lblItemPrice;
    public Label lblMedicineDescription;
    public Label lblMedicinePrice;
    public Label lblServiceDescription;
    public Label lblServicePrice;
    public TextField txtMedicineQuantity;
    public TextField txtItemQuantity;
    public TableColumn colNo;
    public TableColumn colDescription;
    public TableColumn colQuantity;
    public TableColumn colPrice;
    public TableColumn colTotal;
    public TableView tblPayment;
    public Label lblNetTotal;
    public Label lblOrderDate;
    public TableView tblMedicinePayment;
    public TableColumn colMedicineID;
    public TableColumn colMedicineDescription;
    public TableColumn colMedicineQuantity;
    public TableColumn colMedicinePrice;
    public TableColumn colMedicineTotal;
    public TableView tblServicePayment;
    public TableColumn colServiceNo;
    public TableColumn colServiceDescription;
    public TableColumn colServicePrice;
    public TableColumn colServiceTotal;
    public Button btnMedicinePriceAdd;
    public Button btnMedicineClear;
    public Button btnServicePriceAdd;
    public Button btnServiceClear;
    public ComboBox cmbPetId;
    public Label lblPaymentId;

    private double allNetTotal = 0.0;
    private double itemNetTotal = 0.0;
    private double serviceNetTotal = 0.0;
    private double medicineNetTotal = 0.0;

    private ObservableList<ItemCartTM> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadItemCodes();
        loadMedicineID();
        loadServiceID();
        setCellValueFactory();
        setMedicineCellValueFactory();
        setServiceCellValueFactory();
        setOrderDate();
        generateNextOrderId();

    }


    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        if (backButtonController.backButton == 1) {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/managerDashBoard_form.fxml"))));
            stage.setTitle("Manage");
            stage.centerOnScreen();
            stage.show();
        } else {
            System.out.println("employee");
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/receptionistDashBoard_form.fxml"))));
            stage.setTitle("Manage");
            stage.centerOnScreen();
            stage.show();
        }

    }
    private void generateNextOrderId() {
        try {
            String id = PetItemModel.getNextOrderId();
            lblPaymentId.setText(id);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void txtMedicineCostOnAction(ActionEvent actionEvent) {

    }


    public void txtItemPriceOnAction(ActionEvent actionEvent) {

    }

    private void setOrderDate() {
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    public void btnPetAddOnAction(ActionEvent actionEvent) throws SQLException {
        String petID = txtPetId.getText();
        String ownerId = null;

        boolean isTrue=PetModel.seachID(petID);
        if (isTrue==false) {
            new Alert(Alert.AlertType.CONFIRMATION, "Pet ID not Founded :Wrong Pet ID)").show();
        }

        if (petID.isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "Add Pet ID :)").show();
        }
        if(isTrue==true){
            ResultSet resultSet= PetModel.search(petID);
            if (resultSet.next()) {
                String name = resultSet.getString(2);
                ownerId = resultSet.getString(7);

                lblPetName.setText(name);
            }
            ResultSet ownerResultset= OwnerModel.search(ownerId);
            if (ownerResultset.next()) {
                String cus_name = ownerResultset.getString(2);

                lblOwnerId.setText(cus_name);
            }
        }
    }

    public void btnPriceAddOnAction(ActionEvent actionEvent) throws SQLException {

        String petID = txtPetId.getText();
        if (petID.isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "Pet ID is Required:)").show();
        } else {

            if (txtItemQuantity.getText().isEmpty()) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Quantity is Required:)").show();
            }else {
                String itemCode = (String) cmbItemCode.getValue();
                String itemDes = lblIemDescription.getText();
                double itemPrice = Double.parseDouble(lblItemPrice.getText());
                int itemQuantity = Integer.parseInt(txtItemQuantity.getText());
                double total = itemQuantity * itemPrice;


                if (!obList.isEmpty()) {
                    for (int i = 0; i < tblPayment.getItems().size(); i++) {
                        if (colNo.getCellData(i).equals(itemCode)) {
                            itemQuantity += (int) colQuantity.getCellData(i);
                            total = itemQuantity * (int) itemPrice;

                            obList.get(i).setItemQuantity(itemQuantity);
                            obList.get(i).setTotal(total);

                            tblPayment.refresh();
                            calculateNetTotal();
                            return;
                        }
                    }
                }
                ItemCartTM tm = new ItemCartTM(itemCode, itemDes, itemPrice, itemQuantity, total);

                obList.add(tm);
                tblPayment.setItems(obList);
                calculateNetTotal();

                txtItemQuantity.setText("");
            }

        }

    }

    private void calculateNetTotal() {
        double total = (double) colTotal.getCellData(tblPayment.getItems().size()-1);
        allNetTotal += total;
        itemNetTotal+=total;
        lblNetTotal.setText(String.valueOf(allNetTotal));
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            try{
                int index=tblPayment.getItems().size()-1;
                ItemCartTM itemCartTM = obList.get(index);
                double total = itemCartTM.getTotal();
                allNetTotal = allNetTotal - total;
                lblNetTotal.setText(String.valueOf(allNetTotal));
                tblPayment.refresh();
                obList.remove(tblPayment.getItems().size()-1);
            }catch (Exception e){}
        }

    }

    public void btnPrintOnAction(ActionEvent actionEvent) throws SQLException {

        String petID=txtPetId.getText();
        String paymentId=lblPaymentId.getText();
        String date=lblOrderDate.getText();
        double itemTotal=itemNetTotal;
        double medicineTotal=medicineNetTotal;
        double serviceTotal=serviceNetTotal;
        double allTotal=allNetTotal;

        List<ItemCartDTO> itemCartDTOList = new ArrayList<>();
        for (int i = 0; i <tblPayment.getItems().size(); i++) {
            ItemCartTM itemCartTM = obList.get(i);

            ItemCartDTO dto = new ItemCartDTO(
                    itemCartTM.getItemCode(),
                    itemCartTM.getItemQuantity()
            );
            itemCartDTOList.add(dto);
        }

        List<MedicineCartDTO> medicineCartDTOList = new ArrayList<>();
        for (int i = 0; i <tblMedicinePayment.getItems().size(); i++) {
            MedicineCartTM medicineCartTM = medicineObList.get(i);

            MedicineCartDTO dto = new MedicineCartDTO(
                    medicineCartTM.getMedicineID(),
                    medicineCartTM.getMedicineQuantity()
            );
            medicineCartDTOList.add(dto);
        }

        List<ServiceCartDTO> serviceCartDTOList = new ArrayList<>();
        for (int i = 0; i <tblServicePayment.getItems().size(); i++) {
            ServiceCartTM serviceCartTM = serviceObList.get(i);

            ServiceCartDTO dto = new ServiceCartDTO(
                    serviceCartTM.getServiceID()
            );
            serviceCartDTOList.add(dto);
        }

        boolean isPlaced = false;
        try {
            isPlaced = PaymentModel.payment(petID, paymentId,date,itemTotal,medicineTotal,serviceTotal,allTotal,itemCartDTOList,medicineCartDTOList,serviceCartDTOList);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment Done").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Payment Not Done").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error").show();
        }
        generateNextOrderId();
    }

    private void loadItemCodes() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = PetItemModel.loadCodes();

            for (String code : codes) {
                obList.add(code);
            }
            cmbItemCode.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }
    private void loadMedicineID() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = MedicineModel.loadCodes();

            for (String code : codes) {
                obList.add(code);
            }
            cmbMedicineId.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    private void fillItemFields(PetItem item) {
        lblIemDescription.setText(item.getName());
        lblItemPrice.setText(String.valueOf(item.getPrice()));
    }

    public void cmbItemCodeOnAction(ActionEvent actionEvent) {
        String code = (String) cmbItemCode.getValue();
        try {
            PetItem item = PetItemModel.searchById(code);
            fillItemFields(item);

            txtItemQuantity.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void fillMedicineFields(Medicine medicine) {
        lblMedicineDescription.setText(medicine.getName());
        lblMedicinePrice.setText(String.valueOf(medicine.getPrice()));
    }

    public void cmbMedicineIdOnAction(ActionEvent actionEvent) {
        String id = (String) cmbMedicineId.getValue();
        try {
            Medicine medicine = MedicineModel.searchById(id);
            fillMedicineFields(medicine);

            txtMedicineQuantity.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void loadServiceID() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> ids = ServiceModel.loadCodes();

            for (String id : ids) {
                obList.add(id);
            }
            cmbServiceId.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    public void cmbServiceIdOnAction(ActionEvent actionEvent) {
        String id = (String) cmbServiceId.getValue();
        try {
            Service service = ServiceModel.searchById(id);
            fillServiceFields(service);

            txtMedicineQuantity.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void fillServiceFields(Service service) {
        lblServiceDescription.setText(service.getName());
        lblServicePrice.setText(String.valueOf(service.getCost()));
    }

    void setCellValueFactory() {
        colNo.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("itemDes"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

    }

    void setMedicineCellValueFactory() {
        colMedicineID.setCellValueFactory(new PropertyValueFactory<>("medicineID"));
        colMedicineDescription.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMedicineQuantity.setCellValueFactory(new PropertyValueFactory<>("medicineQuantity"));
        colMedicinePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colMedicineTotal.setCellValueFactory(new PropertyValueFactory<>("medicineTotal"));

    }

    void setServiceCellValueFactory() {
        colServiceNo.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
        colServiceDescription.setCellValueFactory(new PropertyValueFactory<>("serviceDes"));
        colServicePrice.setCellValueFactory(new PropertyValueFactory<>("serviceCharge"));
        colServiceTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    ObservableList<MedicineCartTM> medicineObList = FXCollections.observableArrayList();

    public void btnMedicinePriceAddOnAction(ActionEvent actionEvent) throws SQLException {

        String petID = txtPetId.getText();
        if (petID.isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "Pet ID Not Added :)").show();
        } else {
            if (txtMedicineQuantity.getText().isEmpty()) {
                new Alert(Alert.AlertType.CONFIRMATION, "Medicine Quantity is Required:)").show();
            }else {
                String medicineID = (String) cmbMedicineId.getValue();
                String name = lblMedicineDescription.getText();
                double price = Double.parseDouble(lblMedicinePrice.getText());
                int medicineQuantity = Integer.parseInt(txtMedicineQuantity.getText());
                double total = medicineQuantity * price;


                if (!medicineObList.isEmpty()) {
                    for (int i = 0; i < tblMedicinePayment.getItems().size(); i++) {
                        if (colMedicineID.getCellData(i).equals(medicineID)) {
                            medicineQuantity += (int) colMedicineQuantity.getCellData(i);
                            total = medicineQuantity * (int) price;

                            medicineObList.get(i).setMedicineQuantity(medicineQuantity);
                            medicineObList.get(i).setMedicineTotal(total);

                            tblMedicinePayment.refresh();
                            calculateNetTotal();
                            return;
                        }
                    }
                }
                MedicineCartTM tm = new MedicineCartTM(medicineID, name, medicineQuantity, price, total);

                medicineObList.add(tm);
                tblMedicinePayment.setItems(medicineObList);
                calculateMedicineNetTotal();
                txtMedicineQuantity.setText("");
            }

        }

    }

    public void btnMedicineClearOnAction(ActionEvent actionEvent) throws SQLException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {

            try{
                int index=tblMedicinePayment.getItems().size()-1;
                MedicineCartTM medicineCartTM = medicineObList.get(index);
                double total = medicineCartTM.getMedicineTotal();
                allNetTotal = allNetTotal - total;
                lblNetTotal.setText(String.valueOf(allNetTotal));
                tblMedicinePayment.refresh();
                medicineObList.remove(tblMedicinePayment.getItems().size()-1);
            }catch (Exception e){}
        }
    }

    ObservableList<ServiceCartTM> serviceObList = FXCollections.observableArrayList();

    public void btnServicePriceAddOnAction(ActionEvent actionEvent) throws SQLException {


        String petID = txtPetId.getText();
        if (petID.isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "Pet ID Not Added :)").show();
        } else {
            String petId = txtPetId.getText();
            String date = lblOrderDate.getText();
            String serviceID = String.valueOf(cmbServiceId.getValue());
            String serviceDes = lblServiceDescription.getText();
            double serviceCharge = Integer.parseInt(lblServicePrice.getText());
            double total = Double.parseDouble(String.valueOf(serviceCharge));

            if (!serviceObList.isEmpty()) {
                for (int i = 0; i < tblServicePayment.getItems().size(); i++) {
                    if (colNo.getCellData(i).equals(serviceID)) {
                        total = Double.parseDouble(String.valueOf(serviceCharge));

                        serviceObList.get(i).setServiceCharge(total);

                        tblPayment.refresh();
                        calculateNetTotal();
                        return;
                    }
                }
            }
            ServiceCartTM tm = new ServiceCartTM(serviceID, serviceDes, serviceCharge, total);

            serviceObList.add(tm);
            tblServicePayment.setItems(serviceObList);
            calculateServiceNetTotal();

        }
    }

    public void btnServiceClearOnAction(ActionEvent actionEvent) {

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {

            try{
                int index=tblServicePayment.getItems().size()-1;
                ServiceCartTM serviceCartTM = serviceObList.get(index);
                double total = serviceCartTM.getTotal();
                allNetTotal = allNetTotal - total;
                lblNetTotal.setText(String.valueOf(allNetTotal));
                tblServicePayment.refresh();
                serviceObList.remove(tblServicePayment.getItems().size()-1);

            }catch (Exception e){}
        }


    }

    private void calculateMedicineNetTotal() {
        double total = (double) colMedicineTotal.getCellData(tblMedicinePayment.getItems().size()-1);
        allNetTotal +=total ;
        medicineNetTotal+=total;
        lblNetTotal.setText(String.valueOf(allNetTotal));
    }

    private void calculateServiceNetTotal() {
        double total = (double) colServiceTotal.getCellData(tblServicePayment.getItems().size()-1);
        allNetTotal += total;
        serviceNetTotal+=total;
        lblNetTotal.setText(String.valueOf(allNetTotal));
    }
}
