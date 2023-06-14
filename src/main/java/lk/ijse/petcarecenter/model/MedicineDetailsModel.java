package lk.ijse.petcarecenter.model;

import lk.ijse.petcarecenter.dto.MedicineCartDTO;
import lk.ijse.petcarecenter.util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class MedicineDetailsModel {
    public static boolean add(String petID, String paymentId, List<MedicineCartDTO> medicineCartDTOList, String date) throws SQLException {
        String medicineID=null;
        for(MedicineCartDTO medicineCartDTO : medicineCartDTOList) {
            medicineID=medicineCartDTO.getMedicineID();
        }
        String sql = "INSERT INTO medicinedetail(petID, paymentID, medicineID, date)" +
                "VALUES(?, ?, ?, ?)";

        return CrudUtil.execute(
                sql,
                petID,
                paymentId,
                medicineID,
                date
        );
    }
}
