package lk.ijse.petcarecenter.model;

import lk.ijse.petcarecenter.dto.ItemCartDTO;
import lk.ijse.petcarecenter.dto.PetItem;
import lk.ijse.petcarecenter.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetItemModel {
    public static List<PetItem> getAll() throws SQLException {
        String sql = "SELECT * FROM petitem";

        List<PetItem> data = new ArrayList<>();

        ResultSet resultSet =CrudUtil.execute(sql);
        while (resultSet.next()) {
            data.add(new PetItem(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4)
            ));
        }
        return data;
    }
    public static List<String> loadCodes() throws SQLException {
        String sql= "SELECT itemCode FROM petitem";
        ResultSet resultSet =CrudUtil.execute(sql);

        List<String> data =new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static PetItem searchById(String code) throws SQLException {
        String sql="SELECT * FROM petitem WHERE ItemCode = ?";
        ResultSet resultSet =CrudUtil.execute(sql,code);

        if(resultSet.next()) {
            return new PetItem(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            );
        }
        return null;
    }

    public static String getNextOrderId() throws SQLException {
        String sql = "SELECT paymentID FROM payment ORDER BY paymentID DESC LIMIT 1";

        ResultSet resultSet =CrudUtil.execute(sql);

        if (resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }
    private static String splitOrderId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("PM");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "PM" + id;
        }
        return "PM1";
    }
    public static String getNextItemId() throws SQLException {
        String sql = "SELECT itemCode FROM petitem ORDER BY itemCode DESC LIMIT 1";

        ResultSet resultSet =CrudUtil.execute(sql);

        if (resultSet.next()) {
            return splitItemId(resultSet.getString(1));
        }
        return splitItemId(null);
    }
    private static String splitItemId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("PI");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "PI" + id;
        }
        return "PI1";
    }

    public static boolean add(PetItem petItem) throws SQLException {
        String sql = "INSERT INTO petitem(itemCode, name, price, description)" +
                "VALUES(?, ?, ?, ?)";

        return CrudUtil.execute(
                sql,
                petItem.getItemCode(),
                petItem.getName(),
                petItem.getPrice(),
                petItem.getQuantity()
                );
    }

    public static boolean update(PetItem petItem) throws SQLException {
        String sql = "UPDATE petitem SET name = ?, price = ?,description=? WHERE itemCode=?";

        return CrudUtil.execute(
                sql,
                petItem.getName(),
                petItem.getPrice(),
                petItem.getQuantity(),
                petItem.getItemCode()
        );
    }

    public static boolean delete(PetItem petItem) throws SQLException {
        String sql = "DELETE FROM petitem  WHERE itemCode = ?";
        return CrudUtil.execute(
                sql,
                petItem.getItemCode()
        );
    }

    public static boolean itemQuantityUpdate(List<ItemCartDTO> itemCartDTOList) throws SQLException {
        String itemCode=null;
        int quantity=0;
        int itemQuantity=0;
        int totalQty=0;
        for(ItemCartDTO itemCartDTO : itemCartDTOList) {
            itemCode=itemCartDTO.getItemCode();
            quantity=itemCartDTO.getItemQuantity();
        }
        String sql="SELECT  description FROM petitem WHERE itemCode = ?";

        ResultSet resultSet =CrudUtil.execute(sql,itemCode);
        if (resultSet.next()) {
            itemQuantity = resultSet.getInt(1);
        }
        totalQty=itemQuantity-quantity;

        String newSql = "UPDATE petitem SET   description= ? WHERE itemCode=?";
        return CrudUtil.execute(newSql,totalQty,itemCode);
    }

    public static boolean seachID(String id) throws SQLException {
        String sql="SELECT itemCode FROM petitem";
        ResultSet resultSet =CrudUtil.execute(sql);


        while (resultSet.next()) {
            if(id.equals(resultSet.getString(1))){
                return true;
            }
        }
        return false;
    }
}
