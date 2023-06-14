package lk.ijse.petcarecenter.model;

import lk.ijse.petcarecenter.dto.Owner;
import lk.ijse.petcarecenter.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnerModel {

    public static boolean add(Owner owner) throws SQLException {
        String sql = "INSERT INTO owner( ownerID, name, phoneNumber)" +
                "VALUES(?, ?, ?)";

        return CrudUtil.execute(
                sql,
                owner.getOwnerID(),
                owner.getName(),
                owner.getPhoneNumber()

        );
    }

    public static ResultSet search(String ownerId) throws SQLException {
        String sql = "SELECT * FROM owner WHERE ownerID = ?";
        return CrudUtil.execute(sql,ownerId);
    }

    public static boolean update(Owner owner) throws SQLException {
        String sql = "UPDATE owner SET name = ?, phoneNumber = ? WHERE ownerID = ?";

        return CrudUtil.execute(
                sql,
                owner.getName(),
                owner.getPhoneNumber(),
                owner.getOwnerID()

        );
    }

    public static boolean seachID(String ownerId) throws SQLException {
        String sql ="SELECT ownerID FROM owner";
        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            if(ownerId.equals(resultSet.getString(1))){
                return true;
            }
        }
        return false;
    }
}
