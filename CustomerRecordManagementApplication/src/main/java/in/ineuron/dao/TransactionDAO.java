package in.ineuron.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import in.ineuron.model.Transaction;
import in.ineuron.util.DatabaseConnection;

public class TransactionDAO {

	public List<Transaction> getTransactionsByAccountId(int accountId) throws SQLException {
		List<Transaction> transactions=new ArrayList<>();
		String sql = "SELECT * FROM transaction_history WHERE from_account_id=?";
		try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, accountId);
			ResultSet resultSet = statement.executeQuery();
    		while(resultSet.next()) {
    			Transaction transaction=new Transaction();
    			transaction.setTransactionId(resultSet.getInt("transaction_id"));
    			transaction.setFromAccountId(resultSet.getInt("from_account_id"));
    			transaction.setToAccountId(resultSet.getInt("to_account_id"));
    			transaction.setTransactionDate(resultSet.getDate("transaction_date"));
    			transaction.setTransactionType(resultSet.getString("transaction_type"));
    			transaction.setAmount(resultSet.getDouble("amount"));
    			transaction.setDescription(resultSet.getString("description"));
    			transactions.add(transaction);
    		}
		}
		
		return transactions;
	}
	
	public int getAccountId(String accountName) throws SQLException {
		List<Transaction> transactions=new ArrayList<>();
		String sql = "SELECT account_id FROM bank_accounts WHERE account_number=? AND is_active=1";
		try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1,accountName);
			ResultSet resultSet = statement.executeQuery();
			int accountId=-1;
    		while(resultSet.next()) {
    			accountId=resultSet.getInt("account_id");
    		}
    		return accountId;
		}
	}
	
	public Double checkEnoughBalance(Transaction transaction) throws SQLException {
		String sql = "SELECT balance FROM bank_accounts WHERE account_id=?";
		try (Connection connection = DatabaseConnection.getConnection();
		         PreparedStatement insertStatement = connection.prepareStatement(sql)){
					insertStatement.setInt(1, transaction.getFromAccountId());
					Double amount=0.0;
					ResultSet resultSet = insertStatement.executeQuery();
		    		while(resultSet.next()) {
		    			amount=resultSet.getDouble("balance");
		    		}
		    		return amount;
		         }
		
	}
	
	public int addTransaction(Transaction transaction) throws SQLException {
	    String insertSql = "INSERT INTO transaction_history (from_account_id, to_account_id, transaction_date, transaction_type, amount, description) VALUES (?, ?, ?, ?, ?, ?)";
	    String updateFromSql = "UPDATE bank_accounts SET balance = balance - ? WHERE account_id = ?";
	    String updateToSql = "UPDATE bank_accounts SET balance = balance + ? WHERE account_id = ?";
	    
	    try (Connection connection = DatabaseConnection.getConnection();
	         PreparedStatement insertStatement = connection.prepareStatement(insertSql);
	         PreparedStatement updateFromStatement = connection.prepareStatement(updateFromSql);
	         PreparedStatement updateToStatement = connection.prepareStatement(updateToSql)) {
	        
	        // Start transaction
	        connection.setAutoCommit(false);
	        
	        // Insert transaction history
	        insertStatement.setInt(1, transaction.getFromAccountId());
	        insertStatement.setInt(2, transaction.getToAccountId());
	        insertStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
	        insertStatement.setString(4, transaction.getTransactionType());
	        insertStatement.setDouble(5, transaction.getAmount());
	        insertStatement.setString(6, transaction.getDescription());
	        int rowsAffected = insertStatement.executeUpdate();
	        
	        // Update sender's account balance
	        updateFromStatement.setDouble(1, transaction.getAmount());
	        updateFromStatement.setInt(2, transaction.getFromAccountId());
	        int rowsUpdatedFrom = updateFromStatement.executeUpdate();
	        
	        // Update recipient's account balance
	        updateToStatement.setDouble(1, transaction.getAmount());
	        updateToStatement.setInt(2, transaction.getToAccountId());
	        int rowsUpdatedTo = updateToStatement.executeUpdate();
	        
	        // Commit transaction if all updates are successful
	        if (rowsAffected > 0 && rowsUpdatedFrom > 0 && rowsUpdatedTo > 0) {
	            connection.commit();
	            return rowsAffected;
	        } else {
	            connection.rollback();
	            return -1;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    }
	}

}
