package lk.ijse.petcarecenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.petcarecenter.db.DBConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;

public class ReportManagementFormController {

    public Button btnNext;
    public Button btnService;
    public AnchorPane root;
    public Button btnMedicine;
    public Button btnPayment;

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

    public void btnNextOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/jasper/ServiceDetails.jrxml");
            JRDesignQuery query=new JRDesignQuery();
            query.setText("SELECT date,petID,paymentID,itemCode FROM itemdetail ORDER BY paymentID desc;");
            jasperDesign.setQuery(query);

            JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch ( SQLException | JRException e) {
            e.printStackTrace();
        }
    }

    public void btnServiceOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/jasper/PetServiceDetails.jrxml");
            JRDesignQuery query=new JRDesignQuery();
            query.setText("SELECT date,petID,paymentID,serviceID FROM servicedetail ORDER BY paymentID desc;");
            jasperDesign.setQuery(query);

            JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch ( SQLException | JRException e) {
            e.printStackTrace();
        }

    }

    public void btnMedicineOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/jasper/MedicineDetails.jrxml");
            JRDesignQuery query=new JRDesignQuery();
            query.setText("SELECT date,petID,paymentID,medicineID FROM medicinedetail ORDER BY paymentID desc;");
            jasperDesign.setQuery(query);

            JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch ( SQLException | JRException e) {
            e.printStackTrace();
        }
    }

    public void btnPaymentOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/jasper/Payment.jrxml");
            JRDesignQuery query=new JRDesignQuery();
            query.setText("SELECT paymentID,petID,itemSalary,serviceSalary,doctorSalary,clinicPayment FROM payment ORDER BY paymentID desc;");
            jasperDesign.setQuery(query);

            JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch ( SQLException | JRException e) {
            e.printStackTrace();
        }

    }
}
