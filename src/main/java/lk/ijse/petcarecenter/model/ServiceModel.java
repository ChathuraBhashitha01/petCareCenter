package lk.ijse.petcarecenter.model;

import lk.ijse.petcarecenter.dto.Service;
import lk.ijse.petcarecenter.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceModel {
    public static Service searchById(String id) throws SQLException {
        String sql="SELECT * FROM service WHERE serviceID = ?";
        ResultSet resultSet= CrudUtil.execute(sql,id);

        if (resultSet.next()) {
            return new Service(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3)
            );
        }
        return null;
    }

    public static List<String> loadCodes() throws SQLException {
        String sql="SELECT serviceID FROM service";
        ResultSet resultSet =CrudUtil.execute(sql);

        List<String> data =new ArrayList<>();
        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static boolean seachID(String serviceID) throws SQLException {
        String sql="SELECT serviceID FROM service";
        ResultSet resultSet =CrudUtil.execute(sql);

        while (resultSet.next()) {
            if(serviceID.equals(resultSet.getString(1))){
                return true;
            }
        }
        return false;
    }
}
