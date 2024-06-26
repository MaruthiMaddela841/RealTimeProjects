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
                    admin.setName(resultSet.getString("name"));
                    admin.setEmail(resultSet.getString("email"));
                    admin.setPhone(resultSet.getString("phone"));
                    admin.setAddress(resultSet.getString("address"));
                }
            }
        }
        return admin;
    }

	public void updateAdmin(Admin admin) throws SQLException {
		String sql = "UPDATE admin SET name=?, email=?, phone=?, address=?, username=?, password=? WHERE id=?";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, admin.getName());
			statement.setString(2, admin.getEmail());
			statement.setString(3, admin.getPhone());
			statement.setString(4, admin.getAddress());
			statement.setString(5, admin.getUsername());
			statement.setString(6, admin.getPassword());
			statement.setInt(7, admin.getId());
			statement.executeUpdate();
		}
		
	}

	public Admin getAdmin(int id) throws SQLException {
		String sql = "SELECT * FROM admin WHERE id=?";
		Admin admin=new Admin();
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				admin.setId(resultSet.getInt("id"));
				admin.setName(resultSet.getString("name"));
				admin.setEmail(resultSet.getString("email"));
				admin.setPhone(resultSet.getString("phone"));
				admin.setAddress(resultSet.getString("address"));
				admin.setUsername(resultSet.getString("username"));
				admin.setPassword(resultSet.getString("password"));
			}
		}
		return admin;
	}
}

