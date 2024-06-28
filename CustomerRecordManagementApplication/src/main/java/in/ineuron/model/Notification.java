package in.ineuron.model;

import java.util.Date;

public class Notification {
	
	private int id;
	private int admin_id;
	private int customer_id;
	private String message;
	private Date timestamp;
	private int read;
	
	public Notification() {
		super();
	}
	public Notification(int id, int admin_id, int customer_id, String message, Date timestamp, int read) {
		super();
		this.id = id;
		this.admin_id = admin_id;
		this.customer_id = customer_id;
		this.message = message;
		this.timestamp = timestamp;
		this.read = read;
	}
	@Override
	public String toString() {
		return "Notification [id=" + id + ", admin_id=" + admin_id + ", customer_id=" + customer_id + ", message="
				+ message + ", timestamp=" + timestamp + ", read=" + read + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}

}
