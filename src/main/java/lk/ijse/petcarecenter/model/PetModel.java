package lk.ijse.petcarecenter.model;

import lk.ijse.petcarecenter.dto.Pet;
import lk.ijse.petcarecenter.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PetModel{
    public static String getNextPetId() throws SQLException {
        String sql = "SELECT petID FROM pet ORDER BY petID DESC LIMIT 1";

        ResultSet resultSet =CrudUtil.execute(sql);

        if (resultSet.next()) {
            return splitPetId(resultSet.getString(1));
        }
        return splitPetId(null);
    }
    private static String splitPetId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("P");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "P" + id;
        }
        return "P1";
    }
    public static String getNextOwnerId() throws SQLException {
        String sql = "SELECT ownerID FROM owner ORDER BY ownerID DESC LIMIT 1";

        ResultSet resultSet =CrudUtil.execute(sql);

        if (resultSet.next()) {
            return splitOwnerId(resultSet.getString(1));
        }
        return splitOwnerId(null);
    }
    private static String splitOwnerId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("OW");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "OW" + id;
        }
        return "OW1";
    }

    public static boolean add(Pet pet) throws SQLException {
        String sql = "INSERT INTO pet( petID, name, age, type, bread, ownerID)" +
                "VALUES(?, ?, ?, ?, ?, ?)";

        return CrudUtil.execute(
                sql,
                pet.getPetID(),
                pet.getName(),
                pet.getAge(),
                pet.getType(),
                pet.getBread(),
                pet.getOwnerID()

        );
    }

    public static ResultSet search(String id) throws SQLException {
        String sql = "SELECT * FROM pet WHERE petID = ?";
        return CrudUtil.execute(sql,id);
    }

    public static boolean update(Pet pet) throws SQLException {
        String sql = "UPDATE pet SET name = ?, age = ?, type = ?, bread = ?, ownerID = ? WHERE petID=?";

        return CrudUtil.execute(
                sql,
                pet.getName(),
                pet.getAge(),
                pet.getType(),
                pet.getBread(),
                pet.getOwnerID(),
                pet.getPetID()
        );
    }

    public static boolean delect(Pet pet) throws SQLException {
        String sql = "DELETE FROM pet  WHERE petID = ?";
        return CrudUtil.execute(
                sql,
                pet.getPetID());
    }

    public static boolean seachID(String petID) throws SQLException {
        String sql ="SELECT petID FROM pet";
        ResultSet resultSet =CrudUtil.execute(sql);


        while (resultSet.next()) {
            if(petID.equals(resultSet.getString(1))){
                return true;
            }
        }
        return false;
    }
}
