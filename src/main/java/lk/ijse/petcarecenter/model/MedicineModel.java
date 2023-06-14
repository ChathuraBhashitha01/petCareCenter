package lk.ijse.petcarecenter.model;

import lk.ijse.petcarecenter.dto.Medicine;
import lk.ijse.petcarecenter.dto.MedicineCartDTO;
import lk.ijse.petcarecenter.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineModel {
    public static List<Medicine> getAll() throws SQLException {
        String sql = "SELECT * FROM medicine";

        List<Medicine> data = new ArrayList<>();

        ResultSet resultSet =CrudUtil.execute(sql);
        while (resultSet.next()) {
            data.add(new Medicine(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4)
                    ));
        }
        return data;
    }

    public static List<String> loadCodes() throws SQLException {
        String sql ="SELECT medicineID FROM medicine";
        ResultSet resultSet =CrudUtil.execute(sql);
        List<String> data =new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static Medicine searchById(String code) throws SQLException {
        String sql = "SELECT * FROM medicine WHERE medicineID = ?";

        ResultSet resultSet =CrudUtil.execute(sql,code);
        if(resultSet.next()) {
            return new Medicine(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4)
            );
        }
        return null;
    }
    public static String getNextOrderId() throws SQLException {

        String sql = "SELECT medicineID FROM medicine ORDER BY medicineID DESC LIMIT 1";

        ResultSet resultSet =CrudUtil.execute(sql);

        if (resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }
    private static String splitOrderId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("M");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "M" + id;
        }
        return "M1";
    }

    public static boolean add(Medicine medicine) throws SQLException {

        String sql = "INSERT INTO medicine(medicineID, name, quantity,price)" +
                "VALUES(?, ?, ?,?)";
        return CrudUtil.execute(
                sql,
                medicine.getMedicineID(),
                medicine.getName(),
                medicine.getQuantity(),
                medicine.getPrice());
    }

    public static boolean update(Medicine medicine) throws SQLException {

        String sql = "UPDATE medicine SET name = ?, quantity = ?, price=? WHERE medicineID=?";

        return CrudUtil.execute(
                sql,
                medicine.getName(),
                medicine.getQuantity(),
                medicine.getPrice(),
                medicine.getMedicineID()
                );
    }

    public static boolean delect(Medicine medicine) throws SQLException {

        String sql = "DELETE FROM medicine  WHERE medicineID = ?";

        return CrudUtil.execute(
                sql,
                medicine.getMedicineID()
                );
    }

    public static boolean medicineQuantityUpdate(List<MedicineCartDTO> medicineCartDTOList) throws SQLException {
        String medicineID=null;
        int quantity=0;
        int medicineQuantity=0;
        int totalQty=0;
        for(MedicineCartDTO medicineCartDTO : medicineCartDTOList) {
            medicineID=medicineCartDTO.getMedicineID();
            quantity=medicineCartDTO.getMedicineQuantity();
        }
        String sql="SELECT  quantity FROM medicine WHERE medicineID = ?";

        ResultSet resultSet =CrudUtil.execute(sql,medicineID);
        if (resultSet.next()) {
            medicineQuantity = resultSet.getInt(1);
        }
        totalQty=medicineQuantity-quantity;
        String newSql = "UPDATE medicine SET   quantity= ? WHERE medicineID=?";
        return CrudUtil.execute(
                newSql,
                totalQty,
                medicineID
        );

    }

    public static boolean seachID(String id) throws SQLException {
        String sql="SELECT medicineID FROM medicine";
        ResultSet resultSet =CrudUtil.execute(sql);

        while (resultSet.next()) {
            if(id.equals(resultSet.getString(1))){
                return true;
            }
        }
        return false;
    }
}
