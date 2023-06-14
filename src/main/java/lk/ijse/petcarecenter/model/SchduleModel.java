package lk.ijse.petcarecenter.model;

import lk.ijse.petcarecenter.dto.Schdule;
import lk.ijse.petcarecenter.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SchduleModel {
    public static List<Schdule> getAll() throws SQLException {

        String sql = "SELECT * FROM schdule";

        List<Schdule> data = new ArrayList<>();

        ResultSet resultSet =CrudUtil.execute(sql);
        while (resultSet.next()) {
            data.add(new Schdule(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)

                    ));
        }
        return data;
    }
    public static Schdule search(String code) throws SQLException {

        String sql = "SELECT * FROM schdule WHERE schduleID = ?";

        ResultSet resultSet =CrudUtil.execute(sql,code);
        if(resultSet.next()) {
            return new Schdule(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }
    public static String getNextSchduleId() throws SQLException {
        String sql = "SELECT schduleID FROM schdule ORDER BY schduleID DESC LIMIT 1";

        ResultSet resultSet =CrudUtil.execute(sql);

        if (resultSet.next()) {
            return splitSchduleId(resultSet.getString(1));
        }
        return splitSchduleId(null);
    }
    private static String splitSchduleId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("SC");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "SC" + id;
        }
        return "SC1";
    }

    public static boolean add(Schdule schdule) throws SQLException {
        String sql = "INSERT INTO schdule( schduleID, inTime, outTime, date,doctorID )" +
                "VALUES(?, ?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                schdule.getSchduleID(),
                schdule.getInTime(),
                schdule.getOutTime(),
                schdule.getDate(),
                schdule.getDoctorID()
        );
    }

    public static boolean update(Schdule schdule) throws SQLException {
        String sql = "UPDATE schdule SET inTime = ?, outTime = ?,date=?,doctorID=? WHERE schduleID=?";

        return CrudUtil.execute(
                sql,
                schdule.getInTime(),
                schdule.getOutTime(),
                schdule.getDate(),
                schdule.getDoctorID(),
                schdule.getSchduleID()
        );
    }

    public static boolean delete(Schdule schdule) throws SQLException {
        String sql = "DELETE FROM schdule  WHERE schduleID = ?";

        return CrudUtil.execute(
                sql,
                schdule.getSchduleID()
        );
    }

    public static boolean seachID(String schduleID) throws SQLException {
        String sql="SELECT schduleID FROM schdule";
        ResultSet resultSet =CrudUtil.execute(sql);

        while (resultSet.next()) {
            if(schduleID.equals(resultSet.getString(1))){
                return true;
            }
        }
        return false;
    }
}
