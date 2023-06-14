package lk.ijse.petcarecenter.model;

import lk.ijse.petcarecenter.dto.Appointment;
import lk.ijse.petcarecenter.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentModel {
    public static List<Appointment> getAll() throws SQLException {
        String sql = "SELECT * FROM appointment";

        List<Appointment> data = new ArrayList<>();
        ResultSet resultSet= CrudUtil.execute(sql);
        while (resultSet.next()) {
            data.add(new Appointment(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)

            ));
        }
        return data;
    }
    public static ResultSet search(String code) throws SQLException {
        String sql = "SELECT * FROM appointment WHERE appointmentID = ?";
        return CrudUtil.execute(sql, code);
    }
    public static String getNextAppointmentId() throws SQLException {

        String sql = "SELECT appointmentID FROM appointment ORDER BY appointmentID DESC LIMIT 1";
        ResultSet resultSet=CrudUtil.execute(sql);

        if (resultSet.next()) {
            return splitAppointmentId(resultSet.getString(1));
        }
        return splitAppointmentId(null);
    }
    private static String splitAppointmentId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("AP");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "AP" + id;
        }
        return "AP1";
    }

    public static boolean add(Appointment appointment) throws SQLException {

        String sql = "INSERT INTO appointment(appointmentID, petID, schduleID, date, serviceID)" +
                "VALUES(?, ?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                appointment.getAppointmentID(),
                appointment.getPetID(),
                appointment.getSchduleID(),
                appointment.getDate(),
                appointment.getServiceID()
               );
    }

    public static boolean update(Appointment appointment) throws SQLException { ;
        String sql = "UPDATE appointment SET petID = ?, schduleID = ?,date=?,serviceID=? WHERE appointmentID=?";

        return CrudUtil.execute(
                sql,
                appointment.getPetID(),
                appointment.getSchduleID(),
                appointment.getDate(),
                appointment.getServiceID(),
                appointment.getAppointmentID()
        );
    }

    public static boolean delect(Appointment appointment) throws SQLException {

        String sql = "DELETE FROM appointment  WHERE appointmentID = ?";

        return CrudUtil.execute(
                sql,
                appointment.getAppointmentID()
        );
    }

    public static boolean seachID(String appointmentID) throws SQLException {

        String sql ="SELECT appointmentID FROM appointment";
        ResultSet resultSet= CrudUtil.execute(sql);

        while (resultSet.next()) {
            if(appointmentID.equals(resultSet.getString(1))){
                return true;
            }
        }
        return false;
    }
}
