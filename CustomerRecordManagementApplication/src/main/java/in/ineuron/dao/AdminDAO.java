package in.ineuron.dao;

import in.ineuron.model.Admin;
import in.ineuron.model.Customer;
import in.ineuron.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

    public Admin login(String username, String password) throws SQLException {
        Admin admin = null;
        String sql = "SELECT * FROM admin WHERE username=? AND password=?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    admin = new Admin();
                    admin.setId(resultSet.getInt("id"));
                    admin.setUsername(resultSet.getString("username"));
                    admin.setPassword(resultSet.getString("password"));
                }
            }
        }
        return admin;
    }
}

