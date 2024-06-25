package in.ineuron.model;

import java.sql.Date;

public class Transaction {
    private int transactionId;
    private int fromAccountId;
    private int toAccountId;
    private Date transactionDate;
    private String transactionType;
    private double amount;
    private String description;
	public Transaction() {
		super();
	}
	public Transaction(int transactionId, int fromAccountId, int toAccountId, Date transactionDate,
			String transactionType, double amount, String description) {
		super();
		this.transactionId = transactionId;
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.transactionDate = transactionDate;
		this.transactionType = transactionType;
		this.amount = amount;
		this.description = description;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public int getFromAccountId() {
		return fromAccountId;
	}
	public void setFromAccountId(int fromAccountId) {
		this.fromAccountId = fromAccountId;
	}
	public int getToAccountId() {
		return toAccountId;
	}
	public void setToAccountId(int toAccountId) {
		this.toAccountId = toAccountId;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", fromAccountId=" + fromAccountId + ", toAccountId="
				+ toAccountId + ", transactionDate=" + transactionDate + ", transactionType=" + transactionType
				+ ", amount=" + amount + ", description=" + description + "]";
	}

}

