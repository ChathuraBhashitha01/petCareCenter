package lk.ijse.petcarecenter.model;

import lk.ijse.petcarecenter.dto.AddPassword;
import lk.ijse.petcarecenter.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddPasswordModel {
    public static ResultSet select(String user) throws SQLException {
        String sql = "SELECT userName,password FROM userLogin WHERE user = ?";

        ResultSet resultSet =CrudUtil.execute(sql,user);
        return resultSet;
    }

    public static boolean update(AddPassword addPassword) throws SQLException {
        String sql = "UPDATE userLogin SET userName=?, password = ? WHERE user=?";

        return CrudUtil.execute(
                sql,
                addPassword.getUserName(),
                addPassword.getPassword(),
                addPassword.getUser()
        );
    }
}
