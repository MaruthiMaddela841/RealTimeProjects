package in.ineuron.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import in.ineuron.model.BankAccount;
import in.ineuron.model.Customer;
import in.ineuron.util.DatabaseConnection;

public class BankAccountDAO {
	
	public BankAccount getAccountDetails(int id) throws SQLException {
    	BankAccount bankAccount = new BankAccount();
    	String sql = "SELECT * FROM bank_accounts WHERE customer_id=?";
    	try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
    		statement.setInt(1, id);
    		ResultSet resultSet = statement.executeQuery();
    		while(resultSet.next()) {
    			bankAccount.setAccountId(resultSet.getInt("account_id"));
        		bankAccount.setCustomerId(resultSet.getInt("customer_id"));
        		bankAccount.setAccountNumber(resultSet.getString("account_number"));
        		bankAccount.setAccountType(resultSet.getString("account_type"));
        		bankAccount.setBalance(resultSet.getInt("balance"));
    		}
    	}
    	
    	return bankAccount;
    }
	
	
}

