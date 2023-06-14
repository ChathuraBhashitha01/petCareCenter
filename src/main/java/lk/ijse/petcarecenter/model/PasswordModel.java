package lk.ijse.petcarecenter.model;

import lk.ijse.petcarecenter.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordModel {

    public static ResultSet search(String user) throws SQLException {
        String sql = "SELECT * FROM userLogin WHERE user = ?";
       return CrudUtil.execute(sql,user);
    }
}
