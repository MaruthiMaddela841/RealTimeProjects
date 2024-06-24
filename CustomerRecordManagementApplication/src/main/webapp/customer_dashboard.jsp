<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="in.ineuron.model.BankAccount"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Customer Dashboard</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/customer_dashboard.css">
</head>
<body>
	<div class="container">
		<!-- Header with Logout Button -->
		<header>
			<div class="header-content">
				<h1>Welcome, ${customer.name}!</h1>
			</div>
		</header>

		<!-- Main Content -->
		<div class="main-content">
			<!-- Left Section: Personal Details -->
			<section class="left-section">
				<h2>Personal Details</h2>
				<ul>
					<li><strong>Name:</strong> ${customer.name}</li>
					<li><strong>Email:</strong> ${customer.email}</li>
					<li><strong>Phone:</strong> ${customer.phone}</li>
					<li><strong>Address:</strong> ${customer.address}</li>
					<!-- Add more personal details as needed -->
				</ul>

				<!-- Edit Profile Button -->
				<a
					href="./edit-customer.jsp?id=${customer.id}&name=${customer.name}&email=${customer.email}&phone=${customer.phone}&address=${customer.address}&username=${customer.username}&password=${customer.password}"
					class="btn btn-edit-profile">Edit Profile</a>
				<form action="LogoutServlet" method="POST">
					<button type="submit" class="btn-logout">Logout</button>
				</form>
			</section>

			<!-- Middle Section: Bank Account Details and Transactions -->
			<section class="middle-section">
				<h2>Bank Account Details</h2>
				<table class="account-details">
					<tr>
						<td class="label green"><strong>Account Number:</strong></td>
						<td>${account.accountNumber}</td>
					</tr>
					<tr>
						<td class="label green"><strong>Account Type:</strong></td>
						<td>${account.accountType}</td>
					</tr>
					<tr>
						<td class="label green"><strong>Balance:</strong></td>
						<td>${account.balance}</td>
					</tr>
					<!-- Add more rows for additional account details as needed -->
				</table>


				<!-- Dynamic content placeholder for transaction details or forms -->
				<div id="dynamicContent" class="dynamic-content">
					<!-- Initially empty -->
				</div>

				<!-- Transaction Details Table -->
				<div id="transactionTable" class="transaction-container">
					<h2>Transaction Details</h2>
					<table class="transaction-table">
						<!-- Table Header -->
						<thead>
							<tr>
								<th>Date</th>
								<th>From Account</th>
								<th>To Account</th>
								<th>Type</th>
								<th>Amount</th>
								<th>Description</th>
							</tr>
						</thead>
						<!-- Table Body -->
						<tbody>
							<!-- Iterate over transactions -->
							<c:forEach var="transaction" items="${transactions}">
								<tr>
									<td>${transaction.transactionDate}</td>
									<td>${transaction.fromAccountId}</td>
									<td>${transaction.toAccountId}</td>
									<td>${transaction.transactionType}</td>
									<td>${transaction.amount}</td>
									<td>${transaction.description}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<!-- Buttons for Send Money and Show Transactions -->
				<div id="buttonContainer">
					<button id="sendMoneyBtn" class="btn-send-money"
						onclick="showSendMoneyForm()">Send Money</button>
					<button id="showTransactionsBtn" class="btn-show-transactions"
						style="display: none;" onclick="showTransactions()">Show
						Transactions</button>
				</div>
			</section>

			<!-- Right Section: Notifications -->
			<section class="right-section">
				<h2>Notifications</h2>
				<ul>
					<li><a href="#">Notification 1</a></li>
					<li><a href="#">Notification 2</a></li>
					<!-- List notifications dynamically -->
				</ul>
			</section>
		</div>

		<!-- Footer -->
		<footer>
			<p>&copy; 2024 Customer Record Management</p>
		</footer>
	</div>
	<script>
    function showSendMoneyForm() {
        // Hide the transaction table
        var transactionTable = document.getElementById('transactionTable');
        transactionTable.style.display = 'none';

        // Hide the send money button and show transactions button
        var sendMoneyBtn = document.getElementById('sendMoneyBtn');
        sendMoneyBtn.style.display = 'none';

        var showTransactionsBtn = document.getElementById('showTransactionsBtn');
        showTransactionsBtn.style.display = 'inline-block';

        // Example: You can replace this with any HTML content you want to display dynamically
        var formHtml = `
            <div id="sendMoney" class="show">
                <h2>Send Money Form</h2><br>
                <form action="${pageContext.request.contextPath}/ProcessSendMoneyServlet" method="POST">
                    <label for="fromAccountNo">From Account Number:</label>
                    <input type="text" id="fromAccountNo" value="${account.accountNumber}"name="fromAccountNo" required>
                    <label for="toAccountNo">To Account Number:</label>
                    <input type="text" id="toAccountNo" name="toAccountNo" required>
                    <label for="transactionType">Transaction Type:</label>
                    <select id="transactionType" name="transactionType" required>
                        <option value="NEFT">NEFT</option>
                        <option value="IMPS">IMPS</option>
                        <option value="RTGS">RTGS</option>
                    </select>
                    <label for="amount">Amount:</label>
                    <input type="number" id="amount" name="amount" required>
                    <label for="amount">Amount:</label>
                    <input type="number" id="amount" name="amount" required>
                    <button type="submit">Send Money</button>
                </form>
            </div>
        `;

        // Display the dynamic content
        var dynamicContent = document.getElementById('dynamicContent');
        dynamicContent.innerHTML = formHtml;
        dynamicContent.classList.add('show'); // Show the dynamic content
    }

    function showTransactions() {
        // Show the transaction table
        var transactionTable = document.getElementById('transactionTable');
        transactionTable.style.display = 'block';

        // Clear any dynamic content
        var dynamicContent = document.getElementById('dynamicContent');
        dynamicContent.innerHTML = '';

        // Show send money button and hide show transactions button
        var sendMoneyBtn = document.getElementById('sendMoneyBtn');
        sendMoneyBtn.style.display = 'inline-block';

        var showTransactionsBtn = document.getElementById('showTransactionsBtn');
        showTransactionsBtn.style.display = 'none';
    }
</script>

</body>
</html>
