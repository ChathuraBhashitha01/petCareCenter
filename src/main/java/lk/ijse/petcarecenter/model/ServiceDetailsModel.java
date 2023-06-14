package lk.ijse.petcarecenter.model;

import lk.ijse.petcarecenter.db.DBConnection;
import lk.ijse.petcarecenter.dto.ServiceCartDTO;
import lk.ijse.petcarecenter.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ServiceDetailsModel {
    public static boolean add(String petID, String paymentId, List<ServiceCartDTO> serviceCartDTOList, String date) throws SQLException {
        String serviceID=null;
        for(ServiceCartDTO serviceCartDTO : serviceCartDTOList) {
            serviceID=serviceCartDTO.getServiceID();
        }
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO servicedetail(petID, paymentID, serviceID, date)" +
                "VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, petID);
        pstm.setString(2, paymentId);
        pstm.setString(3, serviceID);
        pstm.setString(4, date);


        return CrudUtil.execute(
                sql,
                petID,
                paymentId,
                serviceID,
                date
        );
    }
}
