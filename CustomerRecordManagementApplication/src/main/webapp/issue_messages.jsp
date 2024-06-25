<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Issue Error</title>
<link rel="stylesheet" type="text/css" href="css/welcome_styles.css">
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f0f0f0;
	margin: 0;
	padding: 20px;
}

h1 {
	text-align: center;
}

table {
	width: 30%;
	margin: 20px auto;
	border-collapse: collapse;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	background-color: #fff;
	text-align: center;
}

th, td {
	padding: 10px;
	text-align: left;
	border: 1px solid #ccc;
	text-align: center;
}

th {
	background-color: #007bff;
	color: white;
	text-align: center;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}

.btn {
	display: inline-block;
	padding: 10px 20px;
	background-color: #007bff;
	color: white;
	text-decoration: none;
	border-radius: 4px;
	transition: background-color 0.3s;
}

.btn:hover {
	background-color: #0056b3;
}

body {
	font-family: Arial, sans-serif;
	background-color: #f0f0f0;
	margin: 20px;
}

.container {
	width: 50%;
	margin: 0 auto;
	background-color: #fff;
	padding: 20px;
	border-radius: 5px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-group {
	margin-bottom: 15px;
}

.form-group label {
	display: block;
	margin-bottom: 5px;
}

.form-group input {
	width: 100%;
	padding: 10px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

.form-group button {
	background-color: #007bff;
	color: white;
	border: none;
	padding: 10px 20px;
	border-radius: 4px;
	cursor: pointer;
}

.form-group button:hover {
	background-color: #0056b3;
}

.form-group.text-center {
	text-align: center;
	/* Aligns content (including inline-block elements like buttons) to center horizontally */
}
</style>
</head>
<body>
	<h1>Issue Error</h1>
	<table>
		<tr>
			<th>Message</th>
		</tr>
		<c:choose>
			<c:when test="${not empty sameAccount and sameAccount eq 1}">
				<tr>
					<td>Sorry Self Transfer is not Possible</td>
				</tr>
			</c:when>
			<c:when
				test="${not empty transactionNotAdded and transactionNotAdded eq -1}">
				<tr>
					<td>Sorry Transaction was unsuccessful</td>
				</tr>
			</c:when>
			<c:when test="${not empty toAccountNo and toAccountNo eq -1}">
				<tr>
					<td>Please enter valid To Account Number and try again!!!</td>
				</tr>
			</c:when>
			<c:when test="${not empty amountFromDb && amountFromDb >= 0}">
				<tr>
					<td>Insufficient Balance!!!</td>
				</tr>
				<th>Account Balance Info</th>
				<tr>
					<td>Your Account Balance is: ${amountFromDb}</td>
				</tr>
				<th>Action</th>
				<tr>
					<td>*** Please Try Again ***</td>
				</tr>
			</c:when>
		</c:choose>
	</table>
	<div class="form-group text-center">
		<a href="${pageContext.request.contextPath}/refreshCustomerDashboard?fromAccountNo=${fromAccountNo}&customerId=${customerId}" class="btn">ACCOUNT HOME</a>
	</div>
</body>
</html>
