<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Issue Book</title>
<style>
/* Basic styling for form */
body {
	font-family: Arial, sans-serif;
	background-color: #f0f0f0;
}

.container {
	width: 50%;
	margin: 50px auto;
	background: #fff;
	padding: 20px;
	border-radius: 5px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
 input[type="text"], input[type="date"], select {
            width: 100%;
            padding: 12px;
            margin-bottom: 15px;
            display: inline-block;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 16px;
        }

input[type=submit] {
	width: 100%;
	background-color: #4CAF50;
	color: white;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

input[type=submit]:hover {
	background-color: #45a049;
}
</style>
</head>
<body>
	<div class="container">
		<h2>Issue Book</h2>
		<form action="./issue/register" method="post">
			<label for="studentId">Student ID:</label> <input type="text"
				id="studentId" name="studentId" required><br> <br>
			<label for="bookId">Book ID:</label> <input type="text" id="bookId"
				name="bookId" required><br> <br>

			<!--             <label for="librarianName">Librarian Name:</label> -->
			<!--             <input type="text" id="librarianName" name="librarianName" required><br><br> -->

			 <label for="librarianName">Librarian Name:</label>
            <select id="librarianName" name="librarianName" required>
                <c:forEach var="librarian" items="${librarians}">
                    <option value="${librarian}">${librarian}</option>
                </c:forEach>
            </select><br><br>
            <label for="issueDate">Issue Date:</label> <input type="date"
				id="issueDate" name="issueDate" required><br> <br>
			<input type="submit" value="Issue Book">
		</form>
	</div>
</body>
</html>
