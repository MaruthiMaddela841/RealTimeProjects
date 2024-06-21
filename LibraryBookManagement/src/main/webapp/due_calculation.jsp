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
	<h1>Due Calculation</h1>
	<table>
		<tr>
			<th>Message</th>
		</tr>
		<c:choose>
			<c:when test="${dueAmount > 0}">
				<tr>
					<td>Number of days between issue_date and due_date
						(inclusive): ${daysBetween}</td>
				</tr>
				<tr>
					<td>Total Amount to be paid: ${dueAmount}</td>
				</tr>
				<tr>
					<td>
						<form
							action="${pageContext.request.contextPath}/issue/deleteIssueRecord"
							method="post">
							<!-- Hidden fields for studentId and bookId -->
							<input type="hidden" id="studentId" name="studentId"
								value="${studentId}"> <input type="hidden" id="bookId"
								name="bookId" value="${bookId}">

							<div class="form-group">
								<div class="checkbox-group">
									<label for="paidReturnedCheckbox">PAID AND RETURNED</label> <input
										type="checkbox" id="paidReturnedCheckbox"
										name="paidReturnedCheckbox"
										onclick="handleCheckboxClick(this)">
								</div>
								<div class="checkbox-group">
									<label for="notPaidCheckbox">NOT PAID</label> <input
										type="checkbox" id="notPaidCheckbox" name="notPaidCheckbox"
										onclick="handleCheckboxClick(this)">
								</div>
							</div>

							<div class="form-group text-center">
								<button type="submit" id="submitButton" class="btn" disabled>Submit</button>
							</div>
						</form>
					</td>
				</tr>
			</c:when>
			<c:when test="${dueAmount == 0}">
				<tr>
					<td>Number of days between issue_date and due_date
						(inclusive): ${daysBetween}</td>
				</tr>
				<tr>
					<td>!!!No Due Amount!!!</td>
				</tr>
				<tr>
					<td>
						<form
							action="${pageContext.request.contextPath}/issue/deleteIssueRecord"
							method="post">
							<!-- Hidden fields for studentId and bookId -->
							<input type="hidden" id="studentId" name="studentId"
								value="${studentId}"> <input type="hidden" id="bookId"
								name="bookId" value="${bookId}">

							<div class="form-group">
								<div class="checkbox-group">
									<label for="paidReturnedCheckbox">PAID AND RETURNED</label> <input
										type="checkbox" id="paidReturnedCheckbox"
										name="paidReturnedCheckbox"
										onclick="handleCheckboxClick(this)">
								</div>
								<div class="checkbox-group">
									<label for="notPaidCheckbox">NOT PAID</label> <input
										type="checkbox" id="notPaidCheckbox" name="notPaidCheckbox"
										onclick="handleCheckboxClick(this)">
								</div>
							</div>

							<div class="form-group text-center">
								<button type="submit" id="submitButton" class="btn" disabled>Submit</button>
							</div>
						</form>
					</td>
				</tr>
			</c:when>
			<c:when test="${dueAmount == -1}">
				<tr>
					<td>Sorry No Records Found!!!</td>
				</tr>
			</c:when>
			<c:when test="${dueAmount == -2}">
				<tr>
					<td>Please pay the due amount and return the book!!!</td>
				</tr>
			</c:when>
			<c:when test="${dueAmount == -3}">
				<tr>
					<td>Please select valid librarian name</td>
				</tr>
			</c:when>
		</c:choose>
	</table>

	<div class="form-group text-center">
		<a href="${pageContext.request.contextPath}/return_book.html"
			class="btn">RETURN BOOK HOME</a>
	</div>

	<script>
		function handleCheckboxClick(clickedCheckbox) {
			var checkboxes = document
					.querySelectorAll('input[type="checkbox"]');
			var atLeastOneChecked = false;

			checkboxes.forEach(function(checkbox) {
				if (checkbox.checked) {
					atLeastOneChecked = true;
				}
			});

			checkboxes.forEach(function(checkbox) {
				checkbox.disabled = false; // Enable all checkboxes first

				// Disable checkboxes other than the clicked one if it's checked
				if (checkbox !== clickedCheckbox && clickedCheckbox.checked) {
					checkbox.disabled = true;
				}
			});

			// Enable submit button only if at least one checkbox is checked
			var submitButton = document.getElementById('submitButton');
			submitButton.disabled = !atLeastOneChecked;
		}
	</script>

</body>
</html>
