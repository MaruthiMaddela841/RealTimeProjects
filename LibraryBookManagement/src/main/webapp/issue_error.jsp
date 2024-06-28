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
			<c:when test="${not empty recordsCount and recordsCount ge 3}">
				<tr>
					<td>Student has already borrowed maximum number of books</td>
				</tr>
			</c:when>
			<c:when test="${not empty duplicate and duplicate eq 1}">
				<tr>
					<td>This book has already been assigned to the Student</td>
				</tr>
			</c:when>
			<c:when test="${not empty bookNotFound and bookNotFound eq -1}">
				<tr>
					<td>!!! Please Enter Correct Book Id !!!</td>
				</tr>
			</c:when>
			<c:when test="${not empty studentNotFound and studentNotFound eq -1}">
				<tr>
					<td>!!! Please Enter Correct Student Id !!!</td>
				</tr>
			</c:when>
		</c:choose>
	</table>
	 <table>
        <tr>
            <th>STUDENT ID</th>
            <th>STUDENT NAME</th>
            <th>BOOK ID</th>
            <th>BOOK TITLE</th>
        </tr>
        <c:forEach var="bookIssue" items="${bookIssues}">
            <tr>
                <td>${bookIssue.studentId}</td>
                <td>${bookIssue.studentName}</td>
                <td>${bookIssue.bookId}</td>
                <td>${bookIssue.bookTitle}</td>
            </tr>
        </c:forEach>
    </table>
	<div class="form-group text-center">
		<a href="${pageContext.request.contextPath}/issue/issueBookBefore"
			class="btn">ISSUE BOOK HOME</a>
	</div>
</body>
</html>
