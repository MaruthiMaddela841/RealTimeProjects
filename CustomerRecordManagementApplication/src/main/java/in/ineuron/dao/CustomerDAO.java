package in.ineuron.dao;

import in.ineuron.model.Customer;
import in.ineuron.util.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setName(resultSet.getString("name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setAddress(resultSet.getString("address"));
                customers.add(customer);
            }
        }
        return customers;
    }

    public void updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET name=?, email=?, phone=?, address=? WHERE id=?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.setString(4, customer.getAddress());
            statement.setInt(5, customer.getId());
            statement.executeUpdate();
        }
    }
}

