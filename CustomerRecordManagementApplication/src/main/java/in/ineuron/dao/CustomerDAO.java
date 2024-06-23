package in.ineuron.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import in.ineuron.model.Customer;
import in.ineuron.util.DatabaseConnection;

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
    
    public void addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (name, email, phone, address, username, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
        	System.out.println("Id: " + customer.getId());
        	System.out.println("Name: " + customer.getName());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Phone: " + customer.getPhone());
            System.out.println("Address: " + customer.getAddress());
            System.out.println("Username: " + customer.getUsername());
            System.out.println("Password: " + customer.getPassword());
               statement.setString(1, customer.getName());
               statement.setString(2, customer.getEmail());
               statement.setString(3, customer.getPhone());
               statement.setString(4, customer.getAddress());
               statement.setString(5, customer.getUsername());
               statement.setString(6, customer.getPassword());
               statement.executeUpdate();
           }
    }

    public void updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET name=?, email=?, phone=?, address=?, username=?, password=? WHERE id=?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.setString(4, customer.getAddress());
            statement.setString(5, customer.getUsername());
            statement.setString(6, customer.getPassword());
            statement.setInt(7, customer.getId());
            statement.executeUpdate();
        }
    }

	public void deleteCustomer(int id) throws SQLException {
		String sql = "DELETE FROM customers WHERE id =?";
		 try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(sql)) {
			 statement.setInt(1, id);
	            statement.executeUpdate();
	        }
	}
	
	public Customer getCustomer(int id) throws SQLException {
		String sql = "SELECT * FROM customers WHERE id=?";
		Customer customer = new Customer();
		 try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(sql)) {
			 statement.setInt(1, id);
	            ResultSet resultSet = statement.executeQuery();
	            while (resultSet.next()) {
	                customer.setId(resultSet.getInt("id"));
	                customer.setName(resultSet.getString("name"));
	                customer.setEmail(resultSet.getString("email"));
	                customer.setPhone(resultSet.getString("phone"));
	                customer.setAddress(resultSet.getString("address"));
	                customer.setUsername(resultSet.getString("username"));
	                customer.setPassword(resultSet.getString("password"));
	            }
	        }
		return customer;
	}
	
	public Customer isCustomerAdmin(String username,String password) throws SQLException {
		String sql = "SELECT * FROM customers WHERE username =? and password=?";
		Customer customer = new Customer();
		 try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(sql)) {
			 statement.setString(1, username);
			 statement.setString(2, password);
	            ResultSet resultSet = statement.executeQuery();
	            while (resultSet.next()) {
	                customer.setId(resultSet.getInt("id"));
	                customer.setName(resultSet.getString("name"));
	                customer.setEmail(resultSet.getString("email"));
	                customer.setPhone(resultSet.getString("phone"));
	                customer.setAddress(resultSet.getString("address"));
	                customer.setUsername(resultSet.getString("username"));
	                customer.setPassword(resultSet.getString("password"));
	            }
	        }
		return customer;
	}
}

