package in.ineuron.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.ineuron.model.BankAccount;
import in.ineuron.model.Transaction;
import in.ineuron.util.DatabaseConnection;

public class TransactionDAO {

	public List<Transaction> getTransactionsByAccountId(int accountId) throws SQLException {
		List<Transaction> transactions=new ArrayList<>();
		String sql = "SELECT * FROM transaction_history WHERE account_id=?";
		try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, accountId);
			ResultSet resultSet = statement.executeQuery();
    		while(resultSet.next()) {
    			Transaction transaction=new Transaction();
    			transaction.setTransactionId(resultSet.getInt("transaction_id"));
    			transaction.setAccountId(resultSet.getInt("account_id"));
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

}
