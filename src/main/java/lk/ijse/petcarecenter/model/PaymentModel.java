package lk.ijse.petcarecenter.model;

import lk.ijse.petcarecenter.db.DBConnection;
import lk.ijse.petcarecenter.dto.ItemCartDTO;
import lk.ijse.petcarecenter.dto.MedicineCartDTO;
import lk.ijse.petcarecenter.dto.Payment;
import lk.ijse.petcarecenter.dto.ServiceCartDTO;
import lk.ijse.petcarecenter.util.CrudUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PaymentModel {

    public static boolean payment(String petID, String paymentId, String date, double itemTotal, double medicineTotal, double serviceTotal, double allTotal, List<ItemCartDTO> itemCartDTOList, List<MedicineCartDTO> medicineCartDTOList, List<ServiceCartDTO> serviceCartDTOList) throws SQLException {
        Connection con=null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            Payment payment=new Payment(paymentId, petID, itemTotal,serviceTotal,medicineTotal,allTotal);
            boolean isSaved = PaymentModel.addPayment(payment);
            if(isSaved) {
                boolean isItemSaved = ItemDetailsModel.add(petID,paymentId,itemCartDTOList,date);
                if(isItemSaved) {
                    boolean isMedicineSaved = MedicineDetailsModel.add(petID,paymentId,medicineCartDTOList,date);
                    if(isMedicineSaved) {
                        boolean isServiceSaved=ServiceDetailsModel.add(petID,paymentId,serviceCartDTOList,date);
                        if (isServiceSaved) {
                            boolean itemQtyUpdated=PetItemModel.itemQuantityUpdate(itemCartDTOList);
                            if(itemQtyUpdated) {
                                boolean medicineQtyUpdated=MedicineModel.medicineQuantityUpdate(medicineCartDTOList);
                                if(medicineQtyUpdated) {
                                    con.commit();
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            return false;
        } catch (SQLException er) {
            con.rollback();
            return false;
        } finally {
            System.out.println("Payment done");
            con.setAutoCommit(true);
        }
    }

    private static boolean addPayment(Payment payment) throws SQLException {
        String sql = "INSERT INTO payment(paymentID, petID, itemSalary, serviceSalary, doctorSalary,clinicPayment)" +
                "VALUES(?, ?, ?, ?, ?, ?)";

        return CrudUtil.execute(
                sql,
                payment.getPaymentID(),
                payment.getPetID(),
                payment.getItemSalary(),
                payment.getServiceSalary(),
                payment.getDoctorSalary(),
                payment.getClinicPayment()
        );
    }
}
