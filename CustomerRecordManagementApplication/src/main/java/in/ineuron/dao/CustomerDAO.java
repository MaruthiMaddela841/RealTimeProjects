package in.ineuron.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
		String sql2 = "SELECT MAX(id) as new_account_id FROM customers";
		String sql3 = "INSERT INTO bank_accounts (customer_id, account_number, account_type, is_active, created_at) VALUES (?, ?, ?, ?, ?)";
		String sql4 = "SELECT MAX(account_number) as max_account_no FROM bank_accounts";
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				PreparedStatement statement2 = connection.prepareStatement(sql2);
				PreparedStatement statement3 = connection.prepareStatement(sql3);
				PreparedStatement statement4 = connection.prepareStatement(sql4)) {
			System.out.println("Id: " + customer.getId());
			System.out.println("Name: " + customer.getName());
			System.out.println("Email: " + customer.getEmail());
			System.out.println("Phone: " + customer.getPhone());
			System.out.println("Address: " + customer.getAddress());
			System.out.println("Username: " + customer.getUsername());
			System.out.println("Password: " + customer.getPassword());
			System.out.println("Account Type: "+customer.getAccountType());
			statement.setString(1, customer.getName());
			statement.setString(2, customer.getEmail());
			statement.setString(3, customer.getPhone());
			statement.setString(4, customer.getAddress());
			statement.setString(5, customer.getUsername());
			statement.setString(6, customer.getPassword());
			statement.executeUpdate();
			ResultSet executeQuery = statement2.executeQuery();
			if (executeQuery.next() && executeQuery.getInt("new_account_id") >= 0) {
				ResultSet executeQuery2 = statement4.executeQuery();
				String newAccountNumber = "";
				if (executeQuery2.next()) {
					String latestNewAccount = executeQuery2.getString("max_account_no");
					latestNewAccount = latestNewAccount.substring(3);
					int latestAccountNumber = Integer.parseInt(latestNewAccount) + 1;
					if (latestAccountNumber < 100) {
						newAccountNumber = "ACC0" + latestAccountNumber;
					} else {
						newAccountNumber = "ACC" + latestAccountNumber;
					}
					System.out.println(newAccountNumber);
				}
				statement3.setInt(1, executeQuery.getInt("new_account_id"));
				statement3.setString(2, newAccountNumber);
				statement3.setString(3, customer.getAccountType());
				statement3.setInt(4, 1);
				statement3.setTimestamp(5,Timestamp.valueOf(LocalDateTime.now()));
				statement3.executeUpdate();
			}
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

	public Customer isCustomerAdmin(String username, String password) throws SQLException {
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
