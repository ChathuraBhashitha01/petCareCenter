package lk.ijse.petcarecenter.model;

import lk.ijse.petcarecenter.dto.Doctor;
import lk.ijse.petcarecenter.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorModel {
    public static List<Doctor> getAll() throws SQLException {
        String sql = "SELECT * FROM doctor";

        List<Doctor> data = new ArrayList<>();

        ResultSet resultSet =CrudUtil.execute(sql);
        while (resultSet.next()) {
            data.add(new Doctor(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3)
            ));
        }
        return data;
    }
    public static Doctor search(String code) throws SQLException {
        String sql = "SELECT * FROM doctor WHERE doctorID = ?";

        ResultSet resultSet = CrudUtil.execute(sql,code);
        if(resultSet.next()) {
            return new Doctor(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3)
            );
        }
        return null;
    }
    public static String getNextDoctorId() throws SQLException {

        String sql = "SELECT doctorID FROM doctor ORDER BY doctorID DESC LIMIT 1";

        ResultSet resultSet =CrudUtil.execute(sql);

        if (resultSet.next()) {
            return splitDoctorId(resultSet.getString(1));
        }
        return splitDoctorId(null);
    }
    private static String splitDoctorId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("DO");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "DO" + id;
        }
        return "DO1";
    }

    public static boolean add(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO doctor(doctorID, name, contact)" +
                "VALUES(?, ?, ?)";
        return CrudUtil.execute(
                sql,
                doctor.getDoctorID(),
                doctor.getName(),
                doctor.getPhoneNumber()
        );
    }

    public static boolean update(Doctor doctor) throws SQLException {
        String sql = "UPDATE doctor SET name = ?, contact = ? WHERE doctorID=?";

        return CrudUtil.execute(
                sql,
                doctor.getName(),
                doctor.getPhoneNumber(),
                doctor.getDoctorID()
        );
    }

    public static boolean delete(Doctor doctor) throws SQLException {
        String sql = "DELETE FROM doctor  WHERE doctorID = ?";

        return CrudUtil.execute(
                sql,
                doctor.getDoctorID()
        );
    }
    public static boolean seachID(String doctorId) throws SQLException {
        String sql="SELECT doctorID FROM doctor";
        ResultSet resultSet =CrudUtil.execute(sql);

        while (resultSet.next()) {
            if(doctorId.equals(resultSet.getString(1))){
                return true;
            }
        }
        return false;
    }
}
