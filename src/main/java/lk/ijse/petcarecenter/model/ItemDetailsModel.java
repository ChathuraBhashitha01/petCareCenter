package lk.ijse.petcarecenter.model;

import lk.ijse.petcarecenter.dto.ItemCartDTO;
import lk.ijse.petcarecenter.util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class ItemDetailsModel {
    public static boolean add(String petID, String paymentId, List<ItemCartDTO> itemCartDTOList, String date) throws SQLException {
        String itemCode=null;
        for(ItemCartDTO itemCartDTO : itemCartDTOList) {
           itemCode=itemCartDTO.getItemCode();
        }
        String sql = "INSERT INTO itemdetail(petID, paymentID, itemCode, date)" +
                "VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(
           sql,
           petID,
           paymentId,
           itemCode,
           date
        );
    }
}
