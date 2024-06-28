package in.ineuron.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;

import in.ineuron.model.Notification;
import in.ineuron.util.DatabaseConnection;

public class NotificationDAO {
	
	public void sendNotificationToCustomer(Notification notification) throws SQLException {
		String sql = "INSERT INTO notification (admin_id, customer_id, message, timestamp, is_read) VALUES (?, ?, ?, ?, ?)";
		try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1,notification.getAdmin_id());
			statement.setInt(2,notification.getCustomer_id());
			statement.setString(3,notification.getMessage());
			statement.setTimestamp(4,Timestamp.valueOf(LocalDateTime.now()));
			statement.setInt(5,1);
			int result = statement.executeUpdate();
    		}
	}
	
	public void markNotification(int id) throws SQLException {
		String sql = "UPDATE notification SET is_read = 0 WHERE id = ?";
		try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1,id);
			int result = statement.executeUpdate();
    		}
	}
	
	public List<Notification> getNotification(int customer_id) throws SQLException {
		List<Notification> notifications = new ArrayList<>();
		String sql = "SELECT * FROM notification WHERE is_read = 1 AND customer_id = ?";
		try (Connection connection = DatabaseConnection.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
		    statement.setInt(1, customer_id);
		    ResultSet result = statement.executeQuery();
		    while (result.next()) {
		        Notification notification = new Notification(); // Create new instance for each row
		        notification.setAdmin_id(result.getInt("admin_id"));
		        notification.setCustomer_id(result.getInt("customer_id"));
		        notification.setId(result.getInt("id"));
		        notification.setMessage(result.getString("message"));
		        notification.setRead(result.getInt("is_read"));
		        notification.setTimestamp(result.getTimestamp("timestamp"));
		        notifications.add(notification);
		    }
		} catch (SQLException e) {
		    e.printStackTrace(); // Handle or log the exception as needed
		}
		return notifications;
		
	}

}
