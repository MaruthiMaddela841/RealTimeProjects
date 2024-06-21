<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Due Calculation</title>
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
	width: 80%;
	margin: 20px auto;
	border-collapse: collapse;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	background-color: #fff;
}

th, td {
	padding: 10px;
	text-align: left;
	border: 1px solid #ccc;
}

th {
	background-color: #007bff;
	color: white;
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

.container {
	width: 50%;
	margin: 0 auto;
	background-color: #fff;
	padding: 20px;
	border-radius: 5px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-group {
	text-align: center;
	margin-bottom: 10px;
}

.form-group label {
	display: inline-block;
	width: 180px; /* Adjust as needed */
	text-align: right;
	margin-right: 10px;
}

.form-group input[type="checkbox"] {
	display: inline-block;
	margin-right: 5px;
}

.checkbox-group {
	display: inline-block;
	vertical-align: middle;
	/* Aligns checkboxes vertically in the middle */
}
</style>
</head>
<body>
	<h1>Invalid Librarian</h1>
	<table>
		<tr>
			<th>Message</th>
		</tr>
		<c:choose>
			<c:when test="${librarianvalidation == -1}">
				<tr>
					<td>Please select valid librarian name</td>
				</tr>
			</c:when>
		</c:choose>
	</table>

	<div class="form-group text-center">
		<a href="${pageContext.request.contextPath}/issue_book.jsp"
			class="btn">ISSUE BOOK HOME</a>
	</div>
</body>
</html>
